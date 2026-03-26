package com.example.mobileprogrammingarchitecture.presentation.ui.screen.add_habit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Habit name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = frequencyInput,
                onValueChange = onFrequencyChange,
                label = { Text("Frequency per week (1-7)") },
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                Text("Enable reminders")
                Switch(checked = remindersEnabled, onCheckedChange = onReminderToggle)
            }
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Button(onClick = onSubmit, enabled = canSubmit) {
                Text("Add habit")
            }
        }
    }
}
