package com.example.mobileprogrammingarchitecture.presentation.ui.screen.habits.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.theme.AppShapes
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme

@Composable
fun HabitListItem(
    habit: Habit,
    modifier: Modifier = Modifier
) {
    val listItemPadding = dimensionResource(R.dimen.padding_list_item)

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.card,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(listItemPadding)) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(R.string.habit_frequency, habit.frequencyPerWeek),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.habit_streak, habit.currentStreak),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = if (habit.reminderEnabled) {
                    stringResource(R.string.habit_reminder_enabled)
                } else {
                    stringResource(R.string.habit_reminder_disabled)
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Habit row", showBackground = true)
@Composable
private fun HabitListItemPreview() {
    HabitTrackerPreviewTheme {
        HabitListItem(
            habit = Habit(
                id = 1,
                name = "10k koraka dnevno",
                frequencyPerWeek = 7,
                currentStreak = 4,
                reminderEnabled = true
            )
        )
    }
}
