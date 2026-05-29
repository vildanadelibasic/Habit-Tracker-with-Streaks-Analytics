package com.example.mobileprogrammingarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRemoteRepository
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val habitRemoteRepository: HabitRemoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val isRefreshing = MutableStateFlow(false)
    private val networkMessage = MutableStateFlow<String?>(null)

    init {
        _uiState.value = HomeUiState.Loading
        habitRepository.observeHabits()
            .catch { e -> _uiState.value = HomeUiState.Error(e.message ?: "Unknown error") }
            .onEach { habits ->
                val refreshing = isRefreshing.value
                val message = networkMessage.value
                _uiState.value = HomeUiState.Success(
                    habits = habits,
                    isRefreshing = refreshing,
                    networkMessage = message
                )
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
        networkMessage
            .onEach { message ->
                val cur = _uiState.value
                if (cur is HomeUiState.Success) {
                    _uiState.value = cur.copy(networkMessage = message)
                }
            }
            .launchIn(viewModelScope)
    }

    fun refreshHabits() {
        if (isRefreshing.value) return
        viewModelScope.launch {
            isRefreshing.update { true }
            try {
                val remoteHabits = habitRemoteRepository.getHabits()
                habitRepository.importRemoteHabits(remoteHabits)
                networkMessage.update {
                    "Synced ${remoteHabits.size} habits from REST API"
                }
            } catch (e: Exception) {
                networkMessage.update {
                    e.message ?: "Network sync failed"
                }
            } finally {
                isRefreshing.update { false }
            }
        }
    }
}
