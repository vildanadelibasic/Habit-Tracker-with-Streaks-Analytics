package com.example.mobileprogrammingarchitecture.presentation.ui.screen.add_habit

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
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
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val sectionLarge = dimensionResource(R.dimen.spacing_section_large)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(screenPadding)
    ) {
        ScreenHeader(
            title = stringResource(R.string.add_habit_title),
            subtitle = stringResource(R.string.add_habit_subtitle)
        )
        Spacer(modifier = Modifier.height(sectionLarge))
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

@Preview(name = "Add habit", showBackground = true)
@Composable
private fun AddHabitScreenPreview() {
    HabitTrackerPreviewTheme {
        AddHabitScreen(
            name = "Study Kotlin",
            frequencyInput = "5",
            remindersEnabled = true,
            errorMessage = null,
            canSubmit = true,
            onNameChange = {},
            onFrequencyChange = {},
            onReminderToggle = {},
            onSubmit = {}
        )
    }
}

@Preview(name = "Add habit — error", showBackground = true)
@Composable
private fun AddHabitScreenErrorPreview() {
    HabitTrackerPreviewTheme {
        AddHabitScreen(
            name = "AB",
            frequencyInput = "99",
            remindersEnabled = false,
            errorMessage = "Frequency must be between 1 and 7 days per week.",
            canSubmit = false,
            onNameChange = {},
            onFrequencyChange = {},
            onReminderToggle = {},
            onSubmit = {}
        )
    }
}

@Preview(name = "Add habit — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddHabitScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        AddHabitScreen(
            name = "",
            frequencyInput = "",
            remindersEnabled = true,
            errorMessage = null,
            canSubmit = false,
            onNameChange = {},
            onFrequencyChange = {},
            onReminderToggle = {},
            onSubmit = {}
        )
    }
}
