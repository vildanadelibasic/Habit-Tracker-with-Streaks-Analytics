package com.example.mobileprogrammingarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.mobileprogrammingarchitecture.data.model.ThemePreference
import com.example.mobileprogrammingarchitecture.data.repository.preferences.UserPreferencesRepository
import com.example.mobileprogrammingarchitecture.presentation.navigation.HabitTrackerScaffold
import com.example.mobileprogrammingarchitecture.presentation.navigation.Screen
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreference by userPreferencesRepository.observeThemePreference()
                .collectAsStateWithLifecycle(initialValue = ThemePreference.System)
            MobileProgrammingArchitectureTheme(themePreference = themePreference) {
                val navController = rememberNavController()
                HabitTrackerScaffold(
                    navController = navController,
                    startDestination = Screen.Home.route
                )
            }
        }
    }
}
