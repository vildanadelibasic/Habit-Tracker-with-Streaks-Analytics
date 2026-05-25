package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

sealed interface RegisterUiState {
    data object Init : RegisterUiState
    data object Loading : RegisterUiState
    data object Success : RegisterUiState
    data class Error(val message: String) : RegisterUiState
}
