package com.example.mobileprogrammingarchitecture.data.repository.cloud

import com.example.mobileprogrammingarchitecture.data.model.HabitData
import kotlinx.coroutines.flow.Flow

interface HabitCloudRepository {
    fun observeCloudHabits(): Flow<List<HabitData>>
    suspend fun upsertHabit(habit: HabitData)
    suspend fun deleteHabit(documentId: String)
}
