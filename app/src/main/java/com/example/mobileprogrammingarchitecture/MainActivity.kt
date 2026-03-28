package com.example.mobileprogrammingarchitecture

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen
import com.example.mobileprogrammingarchitecture.presentation.navigation.AuthScreen
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenSelectorRow
import com.example.mobileprogrammingarchitecture.presentation.ui.preview.PreviewSampleData
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.add_habit.AddHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.analytics.AnalyticsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.habits.HabitsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.home.HomeScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.login.LoginScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile.ProfileScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.registration.RegistrationScreen
import com.example.mobileprogrammingarchitecture.presentation.view_model.HabitTrackerUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.HabitTrackerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileProgrammingArchitectureTheme {
                val vm: HabitTrackerViewModel = viewModel()
                val uiState by vm.uiState.collectAsState()

                if (!uiState.isLoggedIn) {
                    val loginErr = uiState.loginErrorRes?.let { stringResource(it) }
                    val regErr = uiState.registrationErrorRes?.let { stringResource(it) }
                    when (uiState.authScreen) {
                        AuthScreen.LOGIN -> LoginScreen(
                            email = uiState.loginEmail,
                            password = uiState.loginPassword,
                            errorMessage = loginErr,
                            canSubmit = vm.canSubmitLogin(),
                            onEmailChange = vm::onLoginEmailChange,
                            onPasswordChange = vm::onLoginPasswordChange,
                            onSubmit = vm::submitLogin,
                            onGoToRegistration = vm::goToRegistration,
                            modifier = Modifier.fillMaxSize()
                        )
                        AuthScreen.REGISTRATION -> RegistrationScreen(
                            fullName = uiState.regFullName,
                            email = uiState.regEmail,
                            password = uiState.regPassword,
                            confirmPassword = uiState.regConfirmPassword,
                            errorMessage = regErr,
                            canSubmit = vm.canSubmitRegistration(),
                            onFullNameChange = vm::onRegFullNameChange,
                            onEmailChange = vm::onRegEmailChange,
                            onPasswordChange = vm::onRegPasswordChange,
                            onConfirmPasswordChange = vm::onRegConfirmPasswordChange,
                            onSubmit = vm::submitRegistration,
                            onGoToLogin = vm::goToLogin,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    val formErr = uiState.formErrorRes?.let { stringResource(it) }
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            ScreenSelectorRow(
                                selected = uiState.selectedScreen,
                                onSelected = vm::selectScreen
                            )
                        }
                    ) { innerPadding ->
                        HabitTrackerApp(
                            uiState = uiState,
                            formErrorMessage = formErr,
                            onNameChange = vm::onNameInputChange,
                            onFrequencyChange = vm::onFrequencyInputChange,
                            onReminderToggle = vm::onReminderToggle,
                            onSubmitHabit = vm::submitHabit,
                            canSubmit = vm.canSubmitForm(),
                            onClearAll = vm::clearAllHabits,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitTrackerApp(
    uiState: HabitTrackerUiState,
    formErrorMessage: String?,
    onNameChange: (String) -> Unit,
    onFrequencyChange: (String) -> Unit,
    onReminderToggle: (Boolean) -> Unit,
    onSubmitHabit: () -> Unit,
    canSubmit: Boolean,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.selectedScreen) {
        AppScreen.HOME -> HomeScreen(
            totalHabits = uiState.habits.size,
            longestStreak = uiState.habits.maxOfOrNull { it.currentStreak } ?: 0,
            remindersEnabledCount = uiState.habits.count { it.reminderEnabled },
            modifier = modifier
        )

        AppScreen.HABITS -> HabitsScreen(
            habits = uiState.habits,
            onClearAll = onClearAll,
            modifier = modifier
        )

        AppScreen.ADD_HABIT -> AddHabitScreen(
            name = uiState.nameInput,
            frequencyInput = uiState.frequencyInput,
            remindersEnabled = uiState.remindersEnabled,
            errorMessage = formErrorMessage,
            canSubmit = canSubmit,
            onNameChange = onNameChange,
            onFrequencyChange = onFrequencyChange,
            onReminderToggle = onReminderToggle,
            onSubmit = onSubmitHabit,
            modifier = modifier
        )

        AppScreen.ANALYTICS -> AnalyticsScreen(
            habits = uiState.habits,
            modifier = modifier
        )

        AppScreen.PROFILE -> ProfileScreen(modifier = modifier)
    }
}

@Composable
private fun MainShellPreview(
    selectedScreen: AppScreen,
    habits: List<Habit> = PreviewSampleData.habits,
    darkTheme: Boolean = false
) {
    HabitTrackerPreviewTheme(darkTheme = darkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ScreenSelectorRow(
                    selected = selectedScreen,
                    onSelected = {}
                )
            }
        ) { innerPadding ->
            HabitTrackerApp(
                uiState = HabitTrackerUiState(
                    isLoggedIn = true,
                    selectedScreen = selectedScreen,
                    habits = habits
                ),
                formErrorMessage = null,
                onNameChange = {},
                onFrequencyChange = {},
                onReminderToggle = {},
                onSubmitHabit = {},
                canSubmit = true,
                onClearAll = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(name = "App shell — Home", showBackground = true)
@Composable
private fun PreviewShellHome() {
    MainShellPreview(selectedScreen = AppScreen.HOME)
}

@Preview(name = "App shell — Habits", showBackground = true)
@Composable
private fun PreviewShellHabits() {
    MainShellPreview(selectedScreen = AppScreen.HABITS)
}

@Preview(name = "App shell — Add habit", showBackground = true)
@Composable
private fun PreviewShellAddHabit() {
    MainShellPreview(selectedScreen = AppScreen.ADD_HABIT)
}

@Preview(name = "App shell — Analytics", showBackground = true)
@Composable
private fun PreviewShellAnalytics() {
    MainShellPreview(selectedScreen = AppScreen.ANALYTICS)
}

@Preview(name = "App shell — Profile", showBackground = true)
@Composable
private fun PreviewShellProfile() {
    MainShellPreview(selectedScreen = AppScreen.PROFILE)
}

@Preview(name = "App shell — Habits empty", showBackground = true)
@Composable
private fun PreviewShellHabitsEmpty() {
    MainShellPreview(selectedScreen = AppScreen.HABITS, habits = emptyList())
}

@Preview(name = "App shell — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewShellDark() {
    MainShellPreview(selectedScreen = AppScreen.HOME, darkTheme = true)
}
