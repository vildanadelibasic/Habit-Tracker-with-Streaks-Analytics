package com.example.mobileprogrammingarchitecture.presentation.view_model.auth.login

sealed interface LoginUiState {
    data object Init : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}
