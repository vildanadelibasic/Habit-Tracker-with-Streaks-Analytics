package com.example.mobileprogrammingarchitecture.model.repository.habit

import androidx.room.withTransaction
import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.CompletionLogDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitCategoryCrossRefDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.db.AppDatabase
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitCategoryCrossRef
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitCompletionLogEntity
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.model.mapper.HabitMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HabitRepositoryImpl(
    private val database: AppDatabase,
    private val habitDao: HabitDao,
    private val habitCategoryCrossRefDao: HabitCategoryCrossRefDao,
    private val completionLogDao: CompletionLogDao
) : HabitRepository {

    override fun observeHabits(): Flow<List<HabitData>> =
        habitDao.observeHabitsWithCategories()
            .map { rows -> rows.map(HabitMapper::toDomain) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override fun observeHabit(habitId: Int): Flow<HabitData?> =
        habitDao.observeHabitWithCategories(habitId)
            .map { row -> row?.let(HabitMapper::toDomain) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override fun observeCompletionLogCount(): Flow<Int> =
        completionLogDao.observeTotalCount()
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun getNextHabitId(): Int =
        withContext(Dispatchers.IO) {
            habitDao.getMaxHabitId() + 1
        }

    override suspend fun syncHabits() {
        withContext(Dispatchers.IO) {
            delay(SYNC_DELAY_MS)
        }
    }

    override suspend fun importRemoteHabits(habits: List<HabitData>) {
        withContext(Dispatchers.IO) {
            database.withTransaction {
                habits.forEach { remote ->
                    val existing = habitDao.observeHabitWithCategories(remote.id).first()
                    if (existing == null) {
                        habitDao.insertHabit(
                            HabitMapper.toEntity(remote, System.currentTimeMillis())
                        )
                        habitCategoryCrossRefDao.insert(
                            HabitCategoryCrossRef(habitId = remote.id, categoryId = DEFAULT_CATEGORY_ID)
                        )
                    } else {
                        habitDao.updateHabit(
                            HabitMapper.toEntity(remote, existing.habit.createdAtMillis)
                        )
                    }
                }
            }
        }
    }

    override suspend fun insertHabit(habit: HabitData) {
        withContext(Dispatchers.IO) {
            val id = habitDao.getMaxHabitId() + 1
            val toStore = habit.copy(id = id)
            database.withTransaction {
                habitDao.insertHabit(
                    HabitMapper.toEntity(toStore, System.currentTimeMillis())
                )
                habitCategoryCrossRefDao.insert(
                    HabitCategoryCrossRef(habitId = id, categoryId = DEFAULT_CATEGORY_ID)
                )
            }
        }
    }

    override suspend fun updateHabit(habit: HabitData) {
        withContext(Dispatchers.IO) {
            val existing = habitDao.observeHabitWithCategories(habit.id).first() ?: return@withContext
            habitDao.updateHabit(
                HabitMapper.toEntity(habit, existing.habit.createdAtMillis)
            )
        }
    }

    override suspend fun deleteHabit(habitId: Int) {
        withContext(Dispatchers.IO) {
            habitDao.deleteHabitById(habitId)
        }
    }

    override suspend fun setHabitCompleted(habitId: Int, completed: Boolean) {
        withContext(Dispatchers.IO) {
            val row = habitDao.observeHabitWithCategories(habitId).first() ?: return@withContext
            val updated = row.habit.copy(isCompleted = completed)
            habitDao.updateHabit(updated)
            if (completed) {
                completionLogDao.insert(
                    HabitCompletionLogEntity(
                        habitId = habitId,
                        completedAtMillis = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    companion object {
        private const val SYNC_DELAY_MS = 350L
        private const val DEFAULT_CATEGORY_ID = 1
    }
}
