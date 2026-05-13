package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitListFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

sealed interface HabitsUiState {
    data object Init : HabitsUiState
    data object Loading : HabitsUiState
    data class Success(
        val habits: List<HabitData>,
        val searchQuery: String,
        val listFilter: HabitListFilter,
        val sortAlphabetically: Boolean,
        val displayHabits: List<HabitData>,
        val completedCount: Int,
        val isWriteInProgress: Boolean
    ) : HabitsUiState

    data class Error(val message: String) : HabitsUiState
}

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val listFilter = MutableStateFlow(HabitListFilter.All)
    private val sortAlphabetically = MutableStateFlow(false)
    private val pendingWrites = MutableStateFlow(0)

    private val debouncedSearchQuery = searchQuery
        .debounce { query -> if (query.isEmpty()) 0L else SEARCH_DEBOUNCE_MS }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "")

    private data class SearchInputs(val raw: String, val debounced: String)

    private val searchInputs = combine(searchQuery, debouncedSearchQuery) { raw, debounced ->
        SearchInputs(raw, debounced)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SearchInputs("", "")
    )

    private val _uiState = MutableStateFlow<HabitsUiState>(HabitsUiState.Init)
    val uiState: StateFlow<HabitsUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = HabitsUiState.Loading
        combine(
            habitRepository.observeHabits(),
            searchInputs,
            listFilter,
            sortAlphabetically,
            pendingWrites
        ) { habits, search, filter, sortAz, pending ->
            val filtered = habits
                .filter { it.title.contains(search.debounced, ignoreCase = true) }
                .filter {
                    when (filter) {
                        HabitListFilter.All -> true
                        HabitListFilter.Active -> !it.isCompleted
                        HabitListFilter.Completed -> it.isCompleted
                    }
                }
            val display = if (sortAz) {
                filtered.sortedBy { it.title.lowercase(Locale.getDefault()) }
            } else {
                filtered
            }
            HabitsUiState.Success(
                habits = habits,
                searchQuery = search.raw,
                listFilter = filter,
                sortAlphabetically = sortAz,
                displayHabits = display,
                completedCount = habits.count { it.isCompleted },
                isWriteInProgress = pending > 0
            )
        }
            .catch { _uiState.value = HabitsUiState.Error(it.message ?: "Unknown error") }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }

    fun setSearchQuery(value: String) {
        searchQuery.update { value }
    }

    fun setListFilter(value: HabitListFilter) {
        listFilter.update { value }
    }

    fun setSortAlphabetically(value: Boolean) {
        sortAlphabetically.update { value }
    }

    fun toggleHabitCompleted(habitId: Int) {
        viewModelScope.launch {
            pendingWrites.update { it + 1 }
            try {
                val row = habitRepository.observeHabit(habitId).first()
                if (row != null) {
                    habitRepository.setHabitCompleted(habitId, !row.isCompleted)
                }
            } finally {
                pendingWrites.update { (it - 1).coerceAtLeast(0) }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 280L
    }
}
