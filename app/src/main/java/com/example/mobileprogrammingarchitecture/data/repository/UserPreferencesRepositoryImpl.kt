package com.example.mobileprogrammingarchitecture.data.repository

import com.example.mobileprogrammingarchitecture.data.model.ThemePreference
import com.example.mobileprogrammingarchitecture.data.model.local.dao.AppSettingsDao
import com.example.mobileprogrammingarchitecture.data.model.local.entity.AppSettingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val appSettingsDao: AppSettingsDao
) : UserPreferencesRepository {

    override fun observeThemePreference(): Flow<ThemePreference> =
        appSettingsDao.observeSetting(KEY_THEME)
            .map { entity ->
                entity?.value?.let(::parseTheme) ?: ThemePreference.System
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun setThemePreference(preference: ThemePreference) {
        withContext(Dispatchers.IO) {
            delay(PERSIST_DELAY_MS)
            appSettingsDao.upsert(
                AppSettingEntity(
                    settingKey = KEY_THEME,
                    value = preference.name.lowercase()
                )
            )
        }
    }

    private fun parseTheme(raw: String): ThemePreference =
        when (raw.lowercase()) {
            "light" -> ThemePreference.Light
            "dark" -> ThemePreference.Dark
            else -> ThemePreference.System
        }

    companion object {
        private const val KEY_THEME = "theme"
        private const val PERSIST_DELAY_MS = 40L
    }
}
