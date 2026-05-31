package com.example.mobileprogrammingarchitecture.domain.repository

import com.example.mobileprogrammingarchitecture.domain.data.HabitData

interface HabitRemoteRepository {
    suspend fun getHabits(): List<HabitData>
    suspend fun getHabitById(id: Int): HabitData
    suspend fun createHabit(habit: HabitData): HabitData
    suspend fun updateHabit(id: Int, habit: HabitData): HabitData
    suspend fun deleteHabit(id: Int)
}
