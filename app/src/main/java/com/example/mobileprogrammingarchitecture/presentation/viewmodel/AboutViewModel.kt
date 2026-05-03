package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class AboutUiState(
    val habitsInRepository: Int = 0
)

class AboutViewModel(
    habitRepository: HabitRepository
) : ViewModel() {

    val uiState: StateFlow<AboutUiState> = habitRepository.habits
        .map { habits -> AboutUiState(habitsInRepository = habits.size) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AboutUiState()
        )
}
