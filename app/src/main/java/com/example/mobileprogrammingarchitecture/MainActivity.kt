package com.example.mobileprogrammingarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.mobileprogrammingarchitecture.data.model.ThemePreference
import com.example.mobileprogrammingarchitecture.data.repository.auth.AuthRepository
import com.example.mobileprogrammingarchitecture.data.repository.preferences.UserPreferencesRepository
import com.example.mobileprogrammingarchitecture.presentation.navigation.AuthLoadingGate
import com.example.mobileprogrammingarchitecture.presentation.navigation.AuthNavGraph
import com.example.mobileprogrammingarchitecture.presentation.navigation.HabitTrackerScaffold
import com.example.mobileprogrammingarchitecture.presentation.navigation.Screen
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreference by userPreferencesRepository.observeThemePreference()
                .collectAsStateWithLifecycle(initialValue = ThemePreference.System)
            val isLoggedIn by authRepository.observeIsLoggedIn()
                .collectAsStateWithLifecycle(initialValue = null)

            MobileProgrammingArchitectureTheme(themePreference = themePreference) {
                when (isLoggedIn) {
                    null -> AuthLoadingGate()
                    false -> {
                        val authNavController = rememberNavController()
                        AuthNavGraph(
                            navController = authNavController,
                            onAuthenticated = { }
                        )
                    }
                    true -> {
                        val navController = rememberNavController()
                        HabitTrackerScaffold(
                            navController = navController,
                            startDestination = Screen.Home.route,
                            onLogout = { authRepository.logout() }
                        )
                    }
                }
            }
        }
    }
}
