package com.example.mobileprogrammingarchitecture.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mobileprogrammingarchitecture.data.datasource.local.entity.HabitEntity
import com.example.mobileprogrammingarchitecture.data.datasource.local.entity.HabitWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Transaction
    @Query("SELECT * FROM habits ORDER BY habitId ASC")
    fun observeHabitsWithCategories(): Flow<List<HabitWithCategories>>

    @Transaction
    @Query("SELECT * FROM habits WHERE habitId = :habitId LIMIT 1")
    fun observeHabitWithCategories(habitId: Int): Flow<HabitWithCategories?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("DELETE FROM habits WHERE habitId = :habitId")
    suspend fun deleteHabitById(habitId: Int)

    @Query("SELECT COUNT(*) FROM habits")
    suspend fun getHabitCount(): Int

    @Query("SELECT COALESCE(MAX(habitId), 0) FROM habits")
    suspend fun getMaxHabitId(): Int
}
