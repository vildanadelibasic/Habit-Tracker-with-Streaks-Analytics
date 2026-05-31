package com.example.mobileprogrammingarchitecture.model.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitCategoryCrossRef

@Dao
interface HabitCategoryCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ref: HabitCategoryCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(refs: List<HabitCategoryCrossRef>)

    @Query("DELETE FROM habit_category_cross_ref WHERE habitId = :habitId")
    suspend fun deleteAllForHabit(habitId: Int)

    @Query("DELETE FROM habit_category_cross_ref WHERE categoryId = :categoryId")
    suspend fun deleteAllForCategory(categoryId: Int)
}
