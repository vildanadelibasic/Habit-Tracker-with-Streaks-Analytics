package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProfileUiState(
    val totalHabits: Int = 0,
    val completedHabits: Int = 0
)

class ProfileViewModel(
    habitRepository: HabitRepository
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = habitRepository.habits
        .map { habits ->
            ProfileUiState(
                totalHabits = habits.size,
                completedHabits = habits.count { it.isCompleted }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUiState()
        )
}
