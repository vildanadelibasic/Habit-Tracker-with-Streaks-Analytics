package com.example.mobileprogrammingarchitecture.presentation.view_model.profile

sealed interface ProfileUiState {
    data object Init : ProfileUiState
    data object Loading : ProfileUiState
    data class Success(
        val totalHabits: Int,
        val completedHabits: Int,
        val userEmail: String?
    ) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}
