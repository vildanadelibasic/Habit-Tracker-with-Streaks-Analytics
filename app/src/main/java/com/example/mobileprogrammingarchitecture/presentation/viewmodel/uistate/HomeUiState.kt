package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

import com.example.mobileprogrammingarchitecture.data.model.HabitData

sealed interface HomeUiState {
    data object Init : HomeUiState
    data object Loading : HomeUiState
    data class Success(val habits: List<HabitData>, val isRefreshing: Boolean) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
