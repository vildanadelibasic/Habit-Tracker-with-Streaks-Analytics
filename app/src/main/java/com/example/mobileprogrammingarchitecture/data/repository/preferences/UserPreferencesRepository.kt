package com.example.mobileprogrammingarchitecture.data.repository.preferences

import com.example.mobileprogrammingarchitecture.data.model.ThemePreference
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun observeThemePreference(): Flow<ThemePreference>

    suspend fun setThemePreference(preference: ThemePreference)
}
