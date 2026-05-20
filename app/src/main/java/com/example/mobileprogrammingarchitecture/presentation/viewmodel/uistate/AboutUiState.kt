package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

sealed interface AboutUiState {
    data object Init : AboutUiState
    data object Loading : AboutUiState
    data class Success(val habitsInRepository: Int, val completionLogCount: Int) : AboutUiState
    data class Error(val message: String) : AboutUiState
}
