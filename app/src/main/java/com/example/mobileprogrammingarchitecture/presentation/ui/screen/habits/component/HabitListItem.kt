package com.example.mobileprogrammingarchitecture.presentation.ui.screen.habits.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.data.util.Habit

@Composable
fun HabitListItem(
    habit: Habit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = habit.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Frequency: ${habit.frequencyPerWeek}x per week")
            Text(text = "Current streak: ${habit.currentStreak} days")
            Text(
                text = if (habit.reminderEnabled) "Reminder: enabled" else "Reminder: disabled",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
