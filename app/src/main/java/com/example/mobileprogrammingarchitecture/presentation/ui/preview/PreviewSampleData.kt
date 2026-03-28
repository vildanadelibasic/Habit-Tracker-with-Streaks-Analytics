package com.example.mobileprogrammingarchitecture.presentation.ui.preview

import com.example.mobileprogrammingarchitecture.data.util.Habit

object PreviewSampleData {
    val habits = listOf(
        Habit(id = 1, name = "10k koraka dnevno", frequencyPerWeek = 7, currentStreak = 4, reminderEnabled = true),
        Habit(id = 2, name = "Učenje", frequencyPerWeek = 5, currentStreak = 3, reminderEnabled = true),
        Habit(id = 3, name = "Coding", frequencyPerWeek = 6, currentStreak = 5, reminderEnabled = true),
        Habit(id = 4, name = "Druženje", frequencyPerWeek = 3, currentStreak = 2, reminderEnabled = false),
        Habit(id = 5, name = "Spavanje", frequencyPerWeek = 7, currentStreak = 10, reminderEnabled = true),
        Habit(id = 6, name = "2 L vode dnevno", frequencyPerWeek = 7, currentStreak = 7, reminderEnabled = true),
        Habit(id = 7, name = "3 obroka + užine", frequencyPerWeek = 7, currentStreak = 4, reminderEnabled = false)
    )
}
