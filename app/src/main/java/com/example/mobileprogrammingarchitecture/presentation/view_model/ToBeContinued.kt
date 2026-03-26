package com.example.mobileprogrammingarchitecture.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen
import com.example.mobileprogrammingarchitecture.presentation.util.Validation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HabitTrackerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HabitTrackerUiState(
            habits = listOf(
                Habit(id = 1, name = "Morning Walk", frequencyPerWeek = 5, currentStreak = 6, reminderEnabled = true),
                Habit(id = 2, name = "Read 20 Minutes", frequencyPerWeek = 7, currentStreak = 12, reminderEnabled = false)
            )
        )
    )
    val uiState: StateFlow<HabitTrackerUiState> = _uiState

    fun selectScreen(screen: AppScreen) {
        _uiState.update { it.copy(selectedScreen = screen) }
    }

    fun onNameInputChange(value: String) {
        _uiState.update { it.copy(nameInput = value, formError = null) }
    }

    fun onFrequencyInputChange(value: String) {
        _uiState.update { it.copy(frequencyInput = value, formError = null) }
    }

    fun onReminderToggle(enabled: Boolean) {
        _uiState.update { it.copy(remindersEnabled = enabled) }
    }

    fun canSubmitForm(): Boolean {
        val state = _uiState.value
        return Validation.validateHabitName(state.nameInput) == null &&
            Validation.validateFrequency(state.frequencyInput) == null
    }

    fun submitHabit() {
        val state = _uiState.value
        val nameError = Validation.validateHabitName(state.nameInput)
        val frequencyError = Validation.validateFrequency(state.frequencyInput)
        val firstError = nameError ?: frequencyError

        if (firstError != null) {
            _uiState.update { it.copy(formError = firstError) }
            return
        }

        val nextId = (state.habits.maxOfOrNull { it.id } ?: 0) + 1
        val newHabit = Habit(
            id = nextId,
            name = state.nameInput.trim(),
            frequencyPerWeek = state.frequencyInput.toInt(),
            currentStreak = 0,
            reminderEnabled = state.remindersEnabled
        )

        _uiState.update {
            it.copy(
                habits = it.habits + newHabit,
                nameInput = "",
                frequencyInput = "",
                remindersEnabled = true,
                formError = null,
                selectedScreen = AppScreen.HABITS
            )
        }
    }

    fun clearAllHabits() {
        _uiState.update { it.copy(habits = emptyList()) }
    }
}

