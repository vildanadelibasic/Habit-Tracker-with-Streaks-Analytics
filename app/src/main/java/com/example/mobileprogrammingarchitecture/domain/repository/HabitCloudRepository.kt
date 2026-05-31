package com.example.mobileprogrammingarchitecture.domain.repository

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import kotlinx.coroutines.flow.Flow

interface HabitCloudRepository {
    fun observeCloudHabits(): Flow<List<HabitData>>
    suspend fun upsertHabit(habit: HabitData)
    suspend fun deleteHabit(documentId: String)
}
