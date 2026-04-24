package com.example.mobileprogrammingarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.mobileprogrammingarchitecture.presentation.navigation.HabitTrackerScaffold
import com.example.mobileprogrammingarchitecture.presentation.navigation.Screen
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import com.example.mobileprogrammingarchitecture.presentation.theme.ThemePreference
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitSampleDefaults

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themePreference by remember { mutableStateOf(ThemePreference.System) }
            MobileProgrammingArchitectureTheme(themePreference = themePreference) {
                val navController = rememberNavController()
                var habits by remember { mutableStateOf(HabitSampleDefaults.initial) }
                HabitTrackerScaffold(
                    navController = navController,
                    habits = habits,
                    onHabitsChange = { habits = it },
                    themePreference = themePreference,
                    onThemePreferenceChange = { themePreference = it },
                    startDestination = Screen.Home.route
                )
            }
        }
    }
}
