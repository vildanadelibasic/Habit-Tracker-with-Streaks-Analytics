package com.example.mobileprogrammingarchitecture.presentation.view_model.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.domain.repository.AuthRepository
import com.example.mobileprogrammingarchitecture.domain.repository.HabitCloudRepository
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habit.components.HabitListFilter
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.HabitUiState
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

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val habitCloudRepository: HabitCloudRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val listFilter = MutableStateFlow(HabitListFilter.All)
    private val sortAlphabetically = MutableStateFlow(false)
    private val pendingWrites = MutableStateFlow(0)

    private val debouncedSearchQuery = searchQuery
        .debounce { query -> if (query.isEmpty()) 0L else SEARCH_DEBOUNCE_MS }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "")

    private data class SearchInputs(val raw: String, val debounced: String)

    private data class ListInputs(
        val habits: List<HabitData>,
        val search: SearchInputs,
        val filter: HabitListFilter,
        val sortAlphabetically: Boolean,
        val pendingWrites: Int
    )

    private val searchInputs = combine(searchQuery, debouncedSearchQuery) { raw, debounced ->
        SearchInputs(raw, debounced)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SearchInputs("", "")
    )

    private val cloudHabits = habitCloudRepository.observeCloudHabits()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow<HabitUiState>(HabitUiState.Init)
    val uiState: StateFlow<HabitUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = HabitUiState.Loading
        combine(
            combine(
                habitRepository.observeHabits(),
                searchInputs,
                listFilter,
                sortAlphabetically,
                pendingWrites
            ) { habits, search, filter, sortAz, pending ->
                ListInputs(habits, search, filter, sortAz, pending)
            },
            cloudHabits,
            authRepository.observeIsLoggedIn()
        ) { inputs, cloud, isLoggedIn ->
            val habits = inputs.habits
            val search = inputs.search
            val filtered = habits
                .filter { it.title.contains(search.debounced, ignoreCase = true) }
                .filter {
                    when (inputs.filter) {
                        HabitListFilter.All -> true
                        HabitListFilter.Active -> !it.isCompleted
                        HabitListFilter.Completed -> it.isCompleted
                    }
                }
            val display = if (inputs.sortAlphabetically) {
                filtered.sortedBy { it.title.lowercase(Locale.getDefault()) }
            } else {
                filtered
            }
            HabitUiState.Success(
                habits = habits,
                searchQuery = search.raw,
                listFilter = inputs.filter,
                sortAlphabetically = inputs.sortAlphabetically,
                displayHabits = display,
                completedCount = habits.count { it.isCompleted },
                isWriteInProgress = inputs.pendingWrites > 0,
                cloudHabitsCount = if (isLoggedIn) cloud.size else 0
            )
        }
            .catch { _uiState.value = HabitUiState.Error(it.message ?: "Unknown error") }
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
                    if (authRepository.currentUserEmail() != null) {
                        habitCloudRepository.upsertHabit(row.copy(isCompleted = !row.isCompleted))
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
