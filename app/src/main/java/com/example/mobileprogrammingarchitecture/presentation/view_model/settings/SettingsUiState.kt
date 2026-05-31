package com.example.mobileprogrammingarchitecture.presentation.view_model.settings

import com.example.mobileprogrammingarchitecture.domain.data.ThemePreference

sealed interface SettingsUiState {
    data object Init : SettingsUiState
    data object Loading : SettingsUiState
    data class Success(val themePreference: ThemePreference) : SettingsUiState
    data class Error(val message: String) : SettingsUiState
}
