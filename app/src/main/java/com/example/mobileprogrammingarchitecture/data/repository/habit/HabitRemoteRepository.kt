package com.example.mobileprogrammingarchitecture.data.repository.habit

import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.dto.CreateHabitDto
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.dto.UpdateHabitDto

interface HabitRemoteRepository {
    suspend fun getHabits(): List<HabitData>
    suspend fun getHabitById(id: Int): HabitData
    suspend fun createHabit(habit: CreateHabitDto): HabitData
    suspend fun updateHabit(id: Int, habit: UpdateHabitDto): HabitData
    suspend fun deleteHabit(id: Int)
}
