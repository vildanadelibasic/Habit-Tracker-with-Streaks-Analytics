package com.example.mobileprogrammingarchitecture.data.model.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_category_cross_ref",
    primaryKeys = ["habitId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habitId"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("habitId"),
        Index("categoryId")
    ]
)
data class HabitCategoryCrossRef(
    val habitId: Int,
    val categoryId: Int
)
