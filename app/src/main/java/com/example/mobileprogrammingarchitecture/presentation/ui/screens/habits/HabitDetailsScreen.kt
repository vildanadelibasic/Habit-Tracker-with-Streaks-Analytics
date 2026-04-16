package com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits

import androidx.compose.foundation.layout.Arrangement
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
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.DetailLabelValueRow
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
    val difficultyLabel = when (habit.difficulty) {
        HabitDifficulty.Easy -> stringResource(R.string.difficulty_easy)
        HabitDifficulty.Medium -> stringResource(R.string.difficulty_medium)
        HabitDifficulty.Hard -> stringResource(R.string.difficulty_hard)
    }
    val typeLabel = if (habit.isDaily) {
        stringResource(R.string.habit_type_daily)
    } else {
        stringResource(R.string.habit_type_flexible)
    }
    val completionLabel = if (habit.isCompleted) {
        stringResource(R.string.habit_marked_complete)
    } else {
        stringResource(R.string.habit_mark_complete)
    }

    HabitDetailsScreenContent(
        habitTitle = habit.title,
        descriptionValue = habit.description.ifBlank { emptyPlaceholder },
        difficultyValue = difficultyLabel,
        typeValue = typeLabel,
        isCompleted = habit.isCompleted,
        completionLabel = completionLabel,
        onToggleCompleted = onToggleCompleted,
        onDelete = onDelete,
        modifier = modifier
    )
}

@Composable
private fun HabitDetailsScreenContent(
    habitTitle: String,
    descriptionValue: String,
    difficultyValue: String,
    typeValue: String,
    isCompleted: Boolean,
    completionLabel: String,
    onToggleCompleted: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    text = habitTitle,
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
                value = descriptionValue
            )
        }
        item {
            DetailLabelValueRow(
                label = stringResource(R.string.details_difficulty),
                value = difficultyValue
            )
        }
        item {
            DetailLabelValueRow(
                label = stringResource(R.string.details_type),
                value = typeValue
            )
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isCompleted, onCheckedChange = { onToggleCompleted() })
                Text(
                    text = completionLabel,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
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
