package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.domain.data.HabitDifficulty
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.AddHabitUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
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
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        FormDraft("", "", HabitDifficulty.Medium, true)
    )

    private val _uiState = MutableStateFlow<AddHabitUiState>(AddHabitUiState.Init)
    val uiState: StateFlow<AddHabitUiState> = _uiState.asStateFlow()

    private val _saveCompleted = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val saveCompleted = _saveCompleted.asSharedFlow()

    init {
        _uiState.value = AddHabitUiState.Loading
        combine(
            formDraft,
            habitRepository.observeHabits(),
            isSaving
        ) { draft, habits, saving ->
            val nextId = (habits.maxOfOrNull { it.id } ?: 0) + 1
            AddHabitUiState.Success(
                title = draft.title,
                description = draft.description,
                difficulty = draft.difficulty,
                isDaily = draft.isDaily,
                nextId = nextId,
                isSaving = saving
            )
        }
            .catch { _uiState.value = AddHabitUiState.Error(it.message ?: "Unknown error") }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }

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
        if (s !is AddHabitUiState.Success || !s.isFormValid || s.isSaving) return
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
                habitRepository.insertHabit(habit)
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
