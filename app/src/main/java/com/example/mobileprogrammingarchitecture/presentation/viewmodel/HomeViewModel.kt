package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data object Init : HomeUiState
    data object Loading : HomeUiState
    data class Success(val habits: List<HabitData>, val isRefreshing: Boolean) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val isRefreshing = MutableStateFlow(false)

    init {
        _uiState.value = HomeUiState.Loading
        habitRepository.observeHabits()
            .catch { e -> _uiState.value = HomeUiState.Error(e.message ?: "Unknown error") }
            .onEach { habits ->
                val refreshing = isRefreshing.value
                _uiState.value = HomeUiState.Success(habits = habits, isRefreshing = refreshing)
            }
            .launchIn(viewModelScope)
        isRefreshing
            .onEach { refreshing ->
                val cur = _uiState.value
                if (cur is HomeUiState.Success) {
                    _uiState.value = cur.copy(isRefreshing = refreshing)
                }
            }
            .launchIn(viewModelScope)
    }

    fun refreshHabits() {
        if (isRefreshing.value) return
        viewModelScope.launch {
            isRefreshing.update { true }
            try {
                habitRepository.syncHabits()
            } finally {
                isRefreshing.update { false }
            }
        }
    }
}
