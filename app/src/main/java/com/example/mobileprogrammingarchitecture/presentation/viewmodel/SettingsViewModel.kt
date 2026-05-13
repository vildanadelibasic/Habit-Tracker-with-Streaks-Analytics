package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.model.ThemePreference
import com.example.mobileprogrammingarchitecture.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SettingsUiState {
    data object Init : SettingsUiState
    data object Loading : SettingsUiState
    data class Success(val themePreference: ThemePreference) : SettingsUiState
    data class Error(val message: String) : SettingsUiState
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Init)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = SettingsUiState.Loading
        userPreferencesRepository.observeThemePreference()
            .catch { _uiState.value = SettingsUiState.Error(it.message ?: "Unknown error") }
            .onEach { theme ->
                _uiState.value = SettingsUiState.Success(themePreference = theme)
            }
            .launchIn(viewModelScope)
    }

    fun setThemePreference(preference: ThemePreference) {
        viewModelScope.launch {
            userPreferencesRepository.setThemePreference(preference)
        }
    }
}
