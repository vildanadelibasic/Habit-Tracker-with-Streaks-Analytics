package com.example.mobileprogrammingarchitecture.presentation.util

object Validation {
    fun validateHabitName(name: String): String? {
        if (name.isBlank()) return "Habit name is required."
        if (name.trim().length < 3) return "Habit name must have at least 3 characters."
        return null
    }

    fun validateFrequency(frequencyInput: String): String? {
        if (frequencyInput.isBlank()) return "Frequency is required."
        val parsed = frequencyInput.toIntOrNull() ?: return "Frequency must be a number."
        if (parsed !in 1..7) return "Frequency must be between 1 and 7 days per week."
        return null
    }
}