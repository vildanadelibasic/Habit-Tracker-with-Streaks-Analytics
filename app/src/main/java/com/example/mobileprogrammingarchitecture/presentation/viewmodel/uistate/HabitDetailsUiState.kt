package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

import com.example.mobileprogrammingarchitecture.data.model.HabitData

sealed interface HabitDetailsUiState {
    data object Init : HabitDetailsUiState
    data object Loading : HabitDetailsUiState
    data class Success(val habit: HabitData, val isMutationInProgress: Boolean) : HabitDetailsUiState
    data class Error(val message: String, val popBack: Boolean = false) : HabitDetailsUiState
}
