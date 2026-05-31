package com.example.mobileprogrammingarchitecture.presentation.view_model.about

sealed interface AboutUiState {
    data object Init : AboutUiState
    data object Loading : AboutUiState
    data class Success(val habitsInRepository: Int, val completionLogCount: Int) : AboutUiState
    data class Error(val message: String) : AboutUiState
}
