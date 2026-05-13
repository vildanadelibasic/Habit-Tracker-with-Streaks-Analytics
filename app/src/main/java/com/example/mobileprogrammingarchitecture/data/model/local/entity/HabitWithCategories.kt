package com.example.mobileprogrammingarchitecture.data.model.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class HabitWithCategories(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "habitId",
        entityColumn = "categoryId",
        associateBy = Junction(HabitCategoryCrossRef::class)
    )
    val categories: List<CategoryEntity>
)
