package com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitDifficulty
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitSampleDefaults

@Composable
fun HabitDetailsScreen(
    habit: HabitData,
    onToggleCompleted: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val emptyPlaceholder = stringResource(R.string.details_empty_placeholder)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = habit.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(R.string.cd_delete_habit),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
        item {
            DetailLabelValueRow(
                label = stringResource(R.string.details_description),
                value = habit.description.ifBlank { emptyPlaceholder }
            )
        }
        item {
            DetailLabelValueRow(
                label = stringResource(R.string.details_difficulty),
                value = when (habit.difficulty) {
                    HabitDifficulty.Easy -> stringResource(R.string.difficulty_easy)
                    HabitDifficulty.Medium -> stringResource(R.string.difficulty_medium)
                    HabitDifficulty.Hard -> stringResource(R.string.difficulty_hard)
                }
            )
        }
        item {
            DetailLabelValueRow(
                label = stringResource(R.string.details_type),
                value = if (habit.isDaily) {
                    stringResource(R.string.habit_type_daily)
                } else {
                    stringResource(R.string.habit_type_flexible)
                }
            )
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = habit.isCompleted, onCheckedChange = { onToggleCompleted() })
                Text(
                    text = if (habit.isCompleted) {
                        stringResource(R.string.habit_marked_complete)
                    } else {
                        stringResource(R.string.habit_mark_complete)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailLabelValueRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true, name = "Habit details")
@Composable
private fun HabitDetailsScreenPreview() {
    HabitTrackerPreviewTheme {
        HabitDetailsScreen(
            habit = HabitSampleDefaults.initial[2],
            onToggleCompleted = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true, name = "Habit details completed")
@Composable
private fun HabitDetailsScreenCompletedPreview() {
    HabitTrackerPreviewTheme {
        HabitDetailsScreen(
            habit = HabitSampleDefaults.initial[4],
            onToggleCompleted = {},
            onDelete = {}
        )
    }
}
