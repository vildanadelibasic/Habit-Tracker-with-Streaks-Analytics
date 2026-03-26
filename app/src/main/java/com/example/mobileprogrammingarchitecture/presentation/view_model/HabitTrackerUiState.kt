package com.example.mobileprogrammingarchitecture.presentation.view_model

import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen

data class HabitTrackerUiState(
    val selectedScreen: AppScreen = AppScreen.HOME,
    val habits: List<Habit> = emptyList(),
    val nameInput: String = "",
    val frequencyInput: String = "",
    val remindersEnabled: Boolean = true,
    val formError: String? = null
)
