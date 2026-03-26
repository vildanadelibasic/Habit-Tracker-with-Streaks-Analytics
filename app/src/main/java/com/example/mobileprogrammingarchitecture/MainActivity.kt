package com.example.mobileprogrammingarchitecture

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenSelectorRow
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.add_habit.AddHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.analytics.AnalyticsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.habits.HabitsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.home.HomeScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile.ProfileScreen
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

@Composable
fun HabitTrackerApp(
    uiState: HabitTrackerUiState,
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
            errorMessage = uiState.formError,
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

@Preview(showBackground = true)
@Composable
fun HabitTrackerAppPreview() {
    MobileProgrammingArchitectureTheme {
        HabitTrackerApp(
            uiState = HabitTrackerUiState(),
            onNameChange = {},
            onFrequencyChange = {},
            onReminderToggle = {},
            onSubmitHabit = {},
            canSubmit = false,
            onClearAll = {}
        )
    }
}