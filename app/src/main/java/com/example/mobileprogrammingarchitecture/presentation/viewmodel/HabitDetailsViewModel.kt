package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HabitDetailsUiState(
    val habit: HabitData? = null,
    val hasResolved: Boolean = false,
    val wasEverPresent: Boolean = false,
    val isMutationInProgress: Boolean = false
)

sealed interface HabitDetailsEffect {
    data object Deleted : HabitDetailsEffect
}

class HabitDetailsViewModel(
    private val habitId: Int,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private var sawHabitInRepository = false

    private val pendingMutations = MutableStateFlow(0)

    private val _effects = MutableSharedFlow<HabitDetailsEffect>(extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    val uiState: StateFlow<HabitDetailsUiState> = combine(
        habitRepository.habits,
        pendingMutations
    ) { list, pending ->
        val habit = list.find { it.id == habitId }
        if (habit != null) sawHabitInRepository = true
        HabitDetailsUiState(
            habit = habit,
            hasResolved = true,
            wasEverPresent = sawHabitInRepository,
            isMutationInProgress = pending > 0
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HabitDetailsUiState()
    )

    fun toggleCompleted() {
        viewModelScope.launch {
            pendingMutations.update { it + 1 }
            try {
                habitRepository.updateHabits { list ->
                    list.map { h ->
                        if (h.id == habitId) h.copy(isCompleted = !h.isCompleted) else h
                    }
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
                habitRepository.removeHabit(habitId)
                _effects.emit(HabitDetailsEffect.Deleted)
            } finally {
                pendingMutations.update { (it - 1).coerceAtLeast(0) }
            }
        }
    }
}

class HabitDetailsViewModelFactory(
    private val habitId: Int,
    private val habitRepository: HabitRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitDetailsViewModel::class.java)) {
            return HabitDetailsViewModel(habitId, habitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
