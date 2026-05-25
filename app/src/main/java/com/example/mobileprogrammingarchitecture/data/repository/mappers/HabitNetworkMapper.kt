package com.example.mobileprogrammingarchitecture.data.repository.mappers

import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.model.HabitDifficulty
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.dto.CreateHabitDto
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.dto.HabitDto
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.dto.UpdateHabitDto

object HabitNetworkMapper {

    fun toDomain(dto: HabitDto): HabitData =
        HabitData(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            isCompleted = dto.completed,
            difficulty = frequencyToDifficulty(dto.frequency),
            isDaily = dto.frequency.equals("daily", ignoreCase = true)
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
