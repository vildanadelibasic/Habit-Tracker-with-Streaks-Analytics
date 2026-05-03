package com.example.mobileprogrammingarchitecture.data.repository

import com.example.mobileprogrammingarchitecture.data.model.ThemePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class PreferencesRepository {

    private val _themePreference = MutableStateFlow(ThemePreference.System)

    val themePreference: StateFlow<ThemePreference> = _themePreference.asStateFlow()

    suspend fun setThemePreference(preference: ThemePreference) {
        withContext(Dispatchers.IO) {
            delay(PERSIST_DELAY_MS)
        }
        _themePreference.value = preference
    }

    companion object {
        private const val PERSIST_DELAY_MS = 50L
    }
}
