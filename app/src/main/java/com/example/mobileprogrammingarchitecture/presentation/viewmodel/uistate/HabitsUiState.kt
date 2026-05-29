package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitListFilter

sealed interface HabitsUiState {
    data object Init : HabitsUiState
    data object Loading : HabitsUiState
    data class Success(
        val habits: List<HabitData>,
        val searchQuery: String,
        val listFilter: HabitListFilter,
        val sortAlphabetically: Boolean,
        val displayHabits: List<HabitData>,
        val completedCount: Int,
        val isWriteInProgress: Boolean,
        val cloudHabitsCount: Int = 0
    ) : HabitsUiState

    data class Error(val message: String) : HabitsUiState
}
