package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

sealed interface LoginUiState {
    data object Init : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}
