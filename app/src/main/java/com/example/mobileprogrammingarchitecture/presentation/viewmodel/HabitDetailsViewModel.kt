package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HabitDetailsUiState {
    data object Init : HabitDetailsUiState
    data object Loading : HabitDetailsUiState
    data class Ready(val habit: HabitData, val isMutationInProgress: Boolean) : HabitDetailsUiState
    data object NotFound : HabitDetailsUiState
    data class Error(val message: String) : HabitDetailsUiState
}

sealed interface HabitDetailsEffect {
    data object Deleted : HabitDetailsEffect
}

@HiltViewModel
class HabitDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val habitId: Int = savedStateHandle.get<Int>("id") ?: -1

    private val pendingMutations = MutableStateFlow(0)

    private val _effects = MutableSharedFlow<HabitDetailsEffect>(extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    private val _uiState = MutableStateFlow<HabitDetailsUiState>(HabitDetailsUiState.Init)
    val uiState: StateFlow<HabitDetailsUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = HabitDetailsUiState.Loading
        combine(
            habitRepository.observeHabits(),
            habitRepository.observeHabit(habitId),
            pendingMutations
        ) { habits, habitOpt, pending ->
            val habit = habitOpt ?: habits.find { it.id == habitId }
            when {
                habit != null -> HabitDetailsUiState.Ready(
                    habit = habit,
                    isMutationInProgress = pending > 0
                )
                habits.isNotEmpty() && habits.none { it.id == habitId } -> HabitDetailsUiState.NotFound
                else -> HabitDetailsUiState.Loading
            }
        }
            .catch { _uiState.value = HabitDetailsUiState.Error(it.message ?: "Unknown error") }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }

    fun toggleCompleted() {
        viewModelScope.launch {
            pendingMutations.update { it + 1 }
            try {
                val habit = habitRepository.observeHabit(habitId).first()
                if (habit != null) {
                    habitRepository.setHabitCompleted(habitId, !habit.isCompleted)
                }
            } finally {
                pendingMutations.update { (it - 1).coerceAtLeast(0) }
            }
        }
    }

    fun deleteHabit() {
        viewModelScope.launch {
            pendingMutations.update { it + 1 }
            try {
                habitRepository.deleteHabit(habitId)
                _effects.emit(HabitDetailsEffect.Deleted)
            } finally {
                pendingMutations.update { (it - 1).coerceAtLeast(0) }
            }
        }
    }
}
