package com.example.mobileprogrammingarchitecture.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_completion_logs",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habitId"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("habitId")]
)
data class HabitCompletionLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0L,
    val habitId: Int,
    val completedAtMillis: Long
)
