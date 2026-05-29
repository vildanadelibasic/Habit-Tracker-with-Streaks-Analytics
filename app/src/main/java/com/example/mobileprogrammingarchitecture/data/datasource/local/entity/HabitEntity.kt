package com.example.mobileprogrammingarchitecture.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey val habitId: Int,
    val title: String,
    val description: String,
    val difficultyOrdinal: Int,
    val isDaily: Boolean,
    val isCompleted: Boolean,
    val createdAtMillis: Long
)
