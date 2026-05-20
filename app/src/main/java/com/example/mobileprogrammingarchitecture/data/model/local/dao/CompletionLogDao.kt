package com.example.mobileprogrammingarchitecture.data.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mobileprogrammingarchitecture.data.model.local.entity.HabitCompletionLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionLogDao {

    @Insert
    suspend fun insert(log: HabitCompletionLogEntity): Long

    @Query("DELETE FROM habit_completion_logs WHERE habitId = :habitId")
    suspend fun deleteAllForHabit(habitId: Int)

    @Query("SELECT COUNT(*) FROM habit_completion_logs")
    fun observeTotalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM habit_completion_logs WHERE habitId = :habitId")
    suspend fun countForHabit(habitId: Int): Int
}
