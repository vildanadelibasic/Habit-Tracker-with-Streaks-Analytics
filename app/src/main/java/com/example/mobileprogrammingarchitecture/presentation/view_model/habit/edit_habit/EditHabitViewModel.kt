package com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit.EditHabitNavigationEvent
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit.EditHabitUiState
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

@HiltViewModel
class EditHabitViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val habitId: Int = savedStateHandle.get<Int>("habitId") ?: -1

    private val pendingMutations = MutableStateFlow(0)

    private val _effects = MutableSharedFlow<EditHabitNavigationEvent>(extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    private val _uiState = MutableStateFlow<EditHabitUiState>(EditHabitUiState.Init)
    val uiState: StateFlow<EditHabitUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = EditHabitUiState.Loading
        combine(
            habitRepository.observeHabits(),
            habitRepository.observeHabit(habitId),
            pendingMutations
        ) { habits, habitOpt, pending ->
            val habit = habitOpt ?: habits.find { it.id == habitId }
            when {
                habit != null -> EditHabitUiState.Success(
                    habit = habit,
                    isMutationInProgress = pending > 0
                )
                habits.isNotEmpty() && habits.none { it.id == habitId } ->
                    EditHabitUiState.Error(message = "", popBack = true)
                else -> EditHabitUiState.Loading
            }
        }
            .catch { _uiState.value = EditHabitUiState.Error(it.message ?: "Unknown error") }
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
                _effects.emit(EditHabitNavigationEvent.Deleted)
            } finally {
                pendingMutations.update { (it - 1).coerceAtLeast(0) }
            }
        }
    }
}
