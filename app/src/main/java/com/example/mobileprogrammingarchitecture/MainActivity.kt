package com.example.mobileprogrammingarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.mobileprogrammingarchitecture.data.model.ThemePreference
import com.example.mobileprogrammingarchitecture.presentation.di.LocalHabitTrackerViewModelFactory
import com.example.mobileprogrammingarchitecture.presentation.navigation.HabitTrackerScaffold
import com.example.mobileprogrammingarchitecture.presentation.navigation.Screen
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as HabitTrackerApplication
        setContent {
            CompositionLocalProvider(
                LocalHabitTrackerViewModelFactory provides app.viewModelFactory
            ) {
                val themePreference by app.preferencesRepository.themePreference.collectAsStateWithLifecycle(
                    initialValue = ThemePreference.System
                )
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
}
