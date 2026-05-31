package com.example.mobileprogrammingarchitecture.presentation.view_model.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Init)
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = RegistrationUiState.Loading
            try {
                authRepository.register(email, password)
                authRepository.login(email, password)
                _uiState.value = RegistrationUiState.Success
            } catch (e: Exception) {
                _uiState.value = RegistrationUiState.Error(
                    e.message ?: "Registration failed. Try a different email."
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = RegistrationUiState.Init
    }
}
