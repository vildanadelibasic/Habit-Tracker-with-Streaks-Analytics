package com.example.mobileprogrammingarchitecture.presentation.view_model.habit

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habit.components.HabitListFilter

sealed interface HabitUiState {
    data object Init : HabitUiState
    data object Loading : HabitUiState
    data class Success(
        val habits: List<HabitData>,
        val searchQuery: String,
        val listFilter: HabitListFilter,
        val sortAlphabetically: Boolean,
        val displayHabits: List<HabitData>,
        val completedCount: Int,
        val isWriteInProgress: Boolean,
        val cloudHabitsCount: Int = 0
    ) : HabitUiState

    data class Error(val message: String) : HabitUiState
}
