package com.example.mobileprogrammingarchitecture.presentation.ui.screen.add_habit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.AppShapes
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme

@Composable
fun HabitFormCard(
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
    val cardPadding = dimensionResource(R.dimen.padding_card)
    val fieldSpacing = dimensionResource(R.dimen.spacing_form_fields)
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
    )

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.card,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(cardPadding),
            verticalArrangement = Arrangement.spacedBy(fieldSpacing)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.add_habit_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                shape = AppShapes.field,
                colors = fieldColors
            )
            OutlinedTextField(
                value = frequencyInput,
                onValueChange = onFrequencyChange,
                label = { Text(stringResource(R.string.add_habit_frequency_label)) },
                modifier = Modifier.fillMaxWidth(),
                shape = AppShapes.field,
                colors = fieldColors
            )
            Column {
                Text(
                    stringResource(R.string.add_habit_reminders),
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = remindersEnabled,
                    onCheckedChange = onReminderToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = onSubmit,
                enabled = canSubmit,
                shape = AppShapes.button,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(stringResource(R.string.add_habit_submit))
            }
        }
    }
}

@Preview(name = "Habit form", showBackground = true)
@Composable
private fun HabitFormCardPreview() {
    HabitTrackerPreviewTheme {
        HabitFormCard(
            name = "Water",
            frequencyInput = "7",
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
