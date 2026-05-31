package com.example.mobileprogrammingarchitecture.presentation.view_model.auth.registration

sealed interface RegistrationUiState {
    data object Init : RegistrationUiState
    data object Loading : RegistrationUiState
    data object Success : RegistrationUiState
    data class Error(val message: String) : RegistrationUiState
}
