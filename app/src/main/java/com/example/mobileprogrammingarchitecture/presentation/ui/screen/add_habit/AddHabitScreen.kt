package com.example.mobileprogrammingarchitecture.presentation.ui.screen.add_habit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.add_habit.component.HabitFormCard

@Composable
fun AddHabitScreen(
    name: String,
    frequencyInput: String,
    remindersEnabled: Boolean,
    errorMessage: String?,
    canSubmit: Boolean,
    onNameChange: (String) -> Unit,
    onFrequencyChange: (String) -> Unit,
    onReminderToggle: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenHeader(
            title = "Add Habit",
            subtitle = "Validation is required before creating a habit."
        )
        HabitFormCard(
            name = name,
            frequencyInput = frequencyInput,
            remindersEnabled = remindersEnabled,
            errorMessage = errorMessage,
            canSubmit = canSubmit,
            onNameChange = onNameChange,
            onFrequencyChange = onFrequencyChange,
            onReminderToggle = onReminderToggle,
            onSubmit = onSubmit
        )
    }
}
