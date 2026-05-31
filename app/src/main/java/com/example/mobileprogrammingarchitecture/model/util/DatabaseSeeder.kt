package com.example.mobileprogrammingarchitecture.model.util

import androidx.room.withTransaction
import com.example.mobileprogrammingarchitecture.domain.data.HabitSampleDefaults
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.AppSettingsDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.CategoryDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.CompletionLogDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitCategoryCrossRefDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.db.AppDatabase
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.AppSettingEntity
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.CategoryEntity
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitCategoryCrossRef
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitCompletionLogEntity
import com.example.mobileprogrammingarchitecture.model.mapper.HabitMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseSeeder(
    private val database: AppDatabase,
    private val habitDao: HabitDao,
    private val categoryDao: CategoryDao,
    private val habitCategoryCrossRefDao: HabitCategoryCrossRefDao,
    private val completionLogDao: CompletionLogDao,
    private val appSettingsDao: AppSettingsDao
) {

    suspend fun seedIfEmpty() {
        withContext(Dispatchers.IO) {
            if (habitDao.getHabitCount() > 0) return@withContext
            database.withTransaction {
                categoryDao.insertAll(
                    listOf(
                        CategoryEntity(1, "Wellness"),
                        CategoryEntity(2, "Study"),
                        CategoryEntity(3, "Social")
                    )
                )
                val createdAt = SEED_TIME
                HabitSampleDefaults.initial.forEach { habit ->
                    habitDao.insertHabit(HabitMapper.toEntity(habit, createdAt))
                }
                habitCategoryCrossRefDao.insertAll(
                    listOf(
                        HabitCategoryCrossRef(1, 1),
                        HabitCategoryCrossRef(2, 2),
                        HabitCategoryCrossRef(3, 2),
                        HabitCategoryCrossRef(4, 3),
                        HabitCategoryCrossRef(5, 1),
                        HabitCategoryCrossRef(6, 1),
                        HabitCategoryCrossRef(7, 1)
                    )
                )
                appSettingsDao.upsert(AppSettingEntity(KEY_THEME, VALUE_SYSTEM))
                completionLogDao.insert(
                    HabitCompletionLogEntity(habitId = 1, completedAtMillis = createdAt)
                )
                completionLogDao.insert(
                    HabitCompletionLogEntity(habitId = 5, completedAtMillis = createdAt + 1)
                )
            }
        }
    }

    companion object {
        private const val SEED_TIME = 1_700_000_000_000L
        private const val KEY_THEME = "theme"
        private const val VALUE_SYSTEM = "system"
    }
}
