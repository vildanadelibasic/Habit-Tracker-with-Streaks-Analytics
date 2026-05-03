package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitListFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

data class HabitsUiState(
    val habits: List<HabitData> = emptyList(),
    val searchQuery: String = "",
    val listFilter: HabitListFilter = HabitListFilter.All,
    val sortAlphabetically: Boolean = false,
    val displayHabits: List<HabitData> = emptyList(),
    val completedCount: Int = 0,
    val isWriteInProgress: Boolean = false
)

class HabitsViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val listFilter = MutableStateFlow(HabitListFilter.All)
    private val sortAlphabetically = MutableStateFlow(false)
    private val pendingWrites = MutableStateFlow(0)

    private val debouncedSearchQuery = searchQuery
        .debounce { query -> if (query.isEmpty()) 0L else SEARCH_DEBOUNCE_MS }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    private data class SearchInputs(val raw: String, val debounced: String)

    private val searchInputs = combine(searchQuery, debouncedSearchQuery) { raw, debounced ->
        SearchInputs(raw = raw, debounced = debounced)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchInputs("", "")
    )

    val uiState: StateFlow<HabitsUiState> = combine(
        habitRepository.habits,
        searchInputs,
        listFilter,
        sortAlphabetically,
        pendingWrites
    ) { habits, search, filter, sortAz, pending ->
        val searchRaw = search.raw
        val searchDebounced = search.debounced
        val filtered = habits
            .filter { it.title.contains(searchDebounced, ignoreCase = true) }
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
        HabitsUiState(
            habits = habits,
            searchQuery = searchRaw,
            listFilter = filter,
            sortAlphabetically = sortAz,
            displayHabits = display,
            completedCount = habits.count { it.isCompleted },
            isWriteInProgress = pending > 0
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HabitsUiState()
    )

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
                habitRepository.updateHabits { list ->
                    list.map { h ->
                        if (h.id == habitId) h.copy(isCompleted = !h.isCompleted) else h
                    }
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
