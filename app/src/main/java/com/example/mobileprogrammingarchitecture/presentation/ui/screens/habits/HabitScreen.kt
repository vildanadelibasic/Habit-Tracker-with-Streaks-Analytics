package com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.components.habits.HabitFilterChipItem
import com.example.mobileprogrammingarchitecture.presentation.ui.components.habits.HabitRowItem
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitSampleDefaults

private enum class HabitListFilter {
    All,
    Active,
    Completed
}

@Composable
fun HabitScreen(
    habits: List<HabitData>,
    onHabitsChange: (List<HabitData>) -> Unit,
    onNavigateToDetails: (HabitData) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var listFilter by remember { mutableStateOf(HabitListFilter.All) }

    val filteredHabits = remember(habits, searchQuery, listFilter) {
        habits
            .filter { it.title.contains(searchQuery, ignoreCase = true) }
            .filter {
                when (listFilter) {
                    HabitListFilter.All -> true
                    HabitListFilter.Active -> !it.isCompleted
                    HabitListFilter.Completed -> it.isCompleted
                }
            }
    }

    val completedCount = remember(habits) { habits.count { it.isCompleted } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.habits_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
        Text(
            text = stringResource(R.string.habits_completed_summary, completedCount, habits.size),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.habits_search_placeholder)) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            singleLine = true
        )

        LazyRow(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                HabitFilterChipItem(
                    label = stringResource(R.string.filter_all),
                    selected = listFilter == HabitListFilter.All,
                    onClick = { listFilter = HabitListFilter.All }
                )
            }
            item {
                HabitFilterChipItem(
                    label = stringResource(R.string.filter_active),
                    selected = listFilter == HabitListFilter.Active,
                    onClick = { listFilter = HabitListFilter.Active }
                )
            }
            item {
                HabitFilterChipItem(
                    label = stringResource(R.string.filter_completed),
                    selected = listFilter == HabitListFilter.Completed,
                    onClick = { listFilter = HabitListFilter.Completed }
                )
            }
        }

        if (filteredHabits.isEmpty()) {
            HabitsEmptyContent(
                message = stringResource(R.string.habits_empty_list)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                items(filteredHabits, key = { it.id }) { habit ->
                    HabitRowItem(
                        title = habit.title,
                        isCompleted = habit.isCompleted,
                        onToggleCompleted = {
                            onHabitsChange(
                                habits.map { h ->
                                    if (h.id == habit.id) h.copy(isCompleted = !h.isCompleted) else h
                                }
                            )
                        },
                        onOpenDetails = { onNavigateToDetails(habit) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HabitsEmptyContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.TaskAlt,
            contentDescription = null,
            modifier = Modifier.padding(bottom = 12.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, name = "Habits")
@Composable
private fun HabitScreenPreview() {
    HabitTrackerPreviewTheme {
        HabitScreen(
            habits = HabitSampleDefaults.initial,
            onHabitsChange = {},
            onNavigateToDetails = {}
        )
    }
}

@Preview(showBackground = true, name = "Habits empty filter")
@Composable
private fun HabitScreenEmptyPreview() {
    HabitTrackerPreviewTheme {
        HabitScreen(
            habits = emptyList(),
            onHabitsChange = {},
            onNavigateToDetails = {}
        )
    }
}
