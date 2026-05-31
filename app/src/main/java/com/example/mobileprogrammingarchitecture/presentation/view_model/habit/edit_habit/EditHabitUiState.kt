package com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit

import com.example.mobileprogrammingarchitecture.domain.data.HabitData

sealed interface EditHabitUiState {
    data object Init : EditHabitUiState
    data object Loading : EditHabitUiState
    data class Success(val habit: HabitData, val isMutationInProgress: Boolean) : EditHabitUiState
    data class Error(val message: String, val popBack: Boolean = false) : EditHabitUiState
}
