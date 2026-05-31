package com.example.mobileprogrammingarchitecture.domain.repository

import com.example.mobileprogrammingarchitecture.domain.data.ThemePreference
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun observeThemePreference(): Flow<ThemePreference>

    suspend fun setThemePreference(preference: ThemePreference)
}
