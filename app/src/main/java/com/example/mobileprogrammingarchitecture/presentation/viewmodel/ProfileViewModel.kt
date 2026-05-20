package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.repository.habit.HabitRepository
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    habitRepository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = ProfileUiState.Loading
        habitRepository.observeHabits()
            .catch { _uiState.value = ProfileUiState.Error(it.message ?: "Unknown error") }
            .onEach { habits ->
                _uiState.value = ProfileUiState.Success(
                    totalHabits = habits.size,
                    completedHabits = habits.count { it.isCompleted }
                )
            }
            .launchIn(viewModelScope)
    }
}
