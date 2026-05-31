package com.example.mobileprogrammingarchitecture.presentation.view_model.habit.add_habit

import com.example.mobileprogrammingarchitecture.domain.data.HabitDifficulty

sealed interface AddHabitUiState {
    data object Init : AddHabitUiState
    data object Loading : AddHabitUiState
    data class Success(
        val title: String,
        val description: String,
        val difficulty: HabitDifficulty,
        val isDaily: Boolean,
        val nextId: Int,
        val isSaving: Boolean
    ) : AddHabitUiState {
        val isFormValid: Boolean get() = title.isNotBlank()
    }

    data class Error(val message: String) : AddHabitUiState
}
