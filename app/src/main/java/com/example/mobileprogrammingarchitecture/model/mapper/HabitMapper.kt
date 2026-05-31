package com.example.mobileprogrammingarchitecture.model.mapper

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.domain.data.HabitDifficulty
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitEntity
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitWithCategories
import com.example.mobileprogrammingarchitecture.model.datasource.network.dto.CreateHabitDto
import com.example.mobileprogrammingarchitecture.model.datasource.network.dto.HabitDto
import com.example.mobileprogrammingarchitecture.model.datasource.network.dto.UpdateHabitDto

object HabitMapper {

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

    fun toDomain(dto: HabitDto): HabitData =
        HabitData(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            isCompleted = dto.completed,
            difficulty = frequencyToDifficulty(dto.frequency),
            isDaily = dto.frequency.equals("daily", ignoreCase = true)
        )

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

    fun toCreateDto(habit: HabitData, userId: Int = 1): CreateHabitDto =
        CreateHabitDto(
            title = habit.title,
            description = habit.description,
            frequency = habitFrequency(habit),
            completed = habit.isCompleted,
            userId = userId
        )

    fun toUpdateDto(habit: HabitData, userId: Int = 1): UpdateHabitDto =
        UpdateHabitDto(
            title = habit.title,
            description = habit.description,
            frequency = habitFrequency(habit),
            completed = habit.isCompleted,
            userId = userId
        )

    private fun habitFrequency(habit: HabitData): String =
        if (habit.isDaily) "daily" else "weekly"

    private fun frequencyToDifficulty(frequency: String): HabitDifficulty =
        when (frequency.lowercase()) {
            "easy", "daily" -> HabitDifficulty.Easy
            "hard" -> HabitDifficulty.Hard
            else -> HabitDifficulty.Medium
        }
}
