package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

import com.example.mobileprogrammingarchitecture.data.model.HabitDifficulty

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
