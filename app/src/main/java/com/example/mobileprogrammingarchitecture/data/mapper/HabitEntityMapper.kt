package com.example.mobileprogrammingarchitecture.data.mapper

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.domain.data.HabitDifficulty
import com.example.mobileprogrammingarchitecture.data.datasource.local.entity.HabitEntity
import com.example.mobileprogrammingarchitecture.data.datasource.local.entity.HabitWithCategories

object HabitEntityMapper {

    fun toDomain(row: HabitWithCategories): HabitData {
        val h = row.habit
        val difficulty = HabitDifficulty.entries.getOrElse(h.difficultyOrdinal.coerceIn(0, 2)) {
            HabitDifficulty.Medium
        }
        return HabitData(
            id = h.habitId,
            title = h.title,
            description = h.description,
            isCompleted = h.isCompleted,
            difficulty = difficulty,
            isDaily = h.isDaily,
            categoryNames = row.categories.map { it.name }
        )
    }

    fun toEntity(data: HabitData, createdAtMillis: Long): HabitEntity =
        HabitEntity(
            habitId = data.id,
            title = data.title,
            description = data.description,
            difficultyOrdinal = data.difficulty.ordinal,
            isDaily = data.isDaily,
            isCompleted = data.isCompleted,
            createdAtMillis = createdAtMillis
        )
}
