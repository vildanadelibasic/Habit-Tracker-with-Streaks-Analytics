package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

import com.example.mobileprogrammingarchitecture.data.model.ThemePreference

sealed interface SettingsUiState {
    data object Init : SettingsUiState
    data object Loading : SettingsUiState
    data class Success(val themePreference: ThemePreference) : SettingsUiState
    data class Error(val message: String) : SettingsUiState
}
