package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.model.HabitDifficulty
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

data class AddHabitUiState(
    val title: String = "",
    val description: String = "",
    val difficulty: HabitDifficulty = HabitDifficulty.Medium,
    val isDaily: Boolean = true,
    val nextId: Int = 1,
    val isSaving: Boolean = false
) {
    val isFormValid: Boolean get() = title.isNotBlank()
}

class AddHabitViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private data class FormDraft(
        val title: String,
        val description: String,
        val difficulty: HabitDifficulty,
        val isDaily: Boolean
    )

    private val title = MutableStateFlow("")
    private val description = MutableStateFlow("")
    private val difficulty = MutableStateFlow(HabitDifficulty.Medium)
    private val isDaily = MutableStateFlow(true)
    private val isSaving = MutableStateFlow(false)

    private val formDraft = combine(title, description, difficulty, isDaily) { t, d, diff, daily ->
        FormDraft(t, d, diff, daily)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FormDraft("", "", HabitDifficulty.Medium, true)
    )

    private val _saveCompleted = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val saveCompleted = _saveCompleted.asSharedFlow()

    val uiState: StateFlow<AddHabitUiState> = combine(
        formDraft,
        habitRepository.habits,
        isSaving
    ) { draft, habits, saving ->
        val nextId = (habits.maxOfOrNull { it.id } ?: 0) + 1
        AddHabitUiState(
            title = draft.title,
            description = draft.description,
            difficulty = draft.difficulty,
            isDaily = draft.isDaily,
            nextId = nextId,
            isSaving = saving
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddHabitUiState()
    )

    fun setTitle(value: String) {
        title.update { value }
    }

    fun setDescription(value: String) {
        description.update { value }
    }

    fun setDifficulty(value: HabitDifficulty) {
        difficulty.update { value }
    }

    fun setIsDaily(value: Boolean) {
        isDaily.update { value }
    }

    fun saveHabit() {
        val s = uiState.value
        if (!s.isFormValid || s.isSaving) return
        val habit = HabitData(
            id = s.nextId,
            title = s.title.trim(),
            description = s.description.trim(),
            isCompleted = false,
            difficulty = s.difficulty,
            isDaily = s.isDaily
        )
        viewModelScope.launch {
            isSaving.update { true }
            try {
                habitRepository.addHabit(habit)
                _saveCompleted.emit(Unit)
                title.update { "" }
                description.update { "" }
                difficulty.update { HabitDifficulty.Medium }
                isDaily.update { true }
            } finally {
                isSaving.update { false }
            }
        }
    }
}
