package com.example.mobileprogrammingarchitecture.data.repository

import com.example.mobileprogrammingarchitecture.data.model.HabitData
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun observeHabits(): Flow<List<HabitData>>

    fun observeHabit(habitId: Int): Flow<HabitData?>

    fun observeCompletionLogCount(): Flow<Int>

    suspend fun getNextHabitId(): Int

    suspend fun syncHabits()

    suspend fun insertHabit(habit: HabitData)

    suspend fun updateHabit(habit: HabitData)

    suspend fun deleteHabit(habitId: Int)

    suspend fun setHabitCompleted(habitId: Int, completed: Boolean)
}
