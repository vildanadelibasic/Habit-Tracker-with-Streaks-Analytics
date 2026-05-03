package com.example.mobileprogrammingarchitecture.data.repository

import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.model.HabitSampleDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

interface HabitRepository {
    val habits: StateFlow<List<HabitData>>

    suspend fun syncHabits()

    suspend fun addHabit(habit: HabitData)

    suspend fun updateHabits(transform: (List<HabitData>) -> List<HabitData>)

    suspend fun removeHabit(id: Int)
}

class DefaultHabitRepository : HabitRepository {

    private val _habits = MutableStateFlow(HabitSampleDefaults.initial)

    private val writeMutex = Mutex()

    override val habits: StateFlow<List<HabitData>> = _habits.asStateFlow()

    override suspend fun syncHabits() {
        withContext(Dispatchers.IO) {
            delay(SYNC_DELAY_MS)
        }
    }

    override suspend fun addHabit(habit: HabitData) {
        writeMutex.withLock {
            withContext(Dispatchers.IO) {
                delay(WRITE_DELAY_MS)
            }
            _habits.update { it + habit }
        }
    }

    override suspend fun updateHabits(transform: (List<HabitData>) -> List<HabitData>) {
        writeMutex.withLock {
            withContext(Dispatchers.IO) {
                delay(WRITE_DELAY_MS)
            }
            _habits.update(transform)
        }
    }

    override suspend fun removeHabit(id: Int) {
        writeMutex.withLock {
            withContext(Dispatchers.IO) {
                delay(WRITE_DELAY_MS)
            }
            _habits.update { list -> list.filter { it.id != id } }
        }
    }

    companion object {
        private const val WRITE_DELAY_MS = 120L
        private const val SYNC_DELAY_MS = 400L
    }
}
