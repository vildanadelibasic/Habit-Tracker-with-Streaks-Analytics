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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.components.habits.HabitFilterChipItem
import com.example.mobileprogrammingarchitecture.presentation.ui.components.habits.HabitRowItem
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitListFilter
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitSampleDefaults
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitsEmptyContent
import java.util.Locale

@Composable
fun HabitScreen(
    habits: List<HabitData>,
    onHabitsChange: (List<HabitData>) -> Unit,
    onNavigateToDetails: (HabitData) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var listFilter by remember { mutableStateOf(HabitListFilter.All) }
    var sortAlphabetically by remember { mutableStateOf(false) }

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

    val displayHabits = remember(filteredHabits, sortAlphabetically) {
        if (sortAlphabetically) {
            filteredHabits.sortedBy { it.title.lowercase(Locale.getDefault()) }
        } else {
            filteredHabits
        }
    }

    val completedCount = remember(habits) { habits.count { it.isCompleted } }

    HabitScreenContent(
        habits = habits,
        completedCount = completedCount,
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        listFilter = listFilter,
        onListFilterChange = { listFilter = it },
        sortAlphabetically = sortAlphabetically,
        onSortAlphabeticallyChange = { sortAlphabetically = it },
        displayHabits = displayHabits,
        onHabitsChange = onHabitsChange,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@Composable
private fun HabitScreenContent(
    habits: List<HabitData>,
    completedCount: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    listFilter: HabitListFilter,
    onListFilterChange: (HabitListFilter) -> Unit,
    sortAlphabetically: Boolean,
    onSortAlphabeticallyChange: (Boolean) -> Unit,
    displayHabits: List<HabitData>,
    onHabitsChange: (List<HabitData>) -> Unit,
    onNavigateToDetails: (HabitData) -> Unit,
    modifier: Modifier = Modifier
) {
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
            onValueChange = onSearchQueryChange,
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
                    onClick = { onListFilterChange(HabitListFilter.All) }
                )
            }
            item {
                HabitFilterChipItem(
                    label = stringResource(R.string.filter_active),
                    selected = listFilter == HabitListFilter.Active,
                    onClick = { onListFilterChange(HabitListFilter.Active) }
                )
            }
            item {
                HabitFilterChipItem(
                    label = stringResource(R.string.filter_completed),
                    selected = listFilter == HabitListFilter.Completed,
                    onClick = { onListFilterChange(HabitListFilter.Completed) }
                )
            }
        }

        Text(
            text = stringResource(R.string.habits_sort_section),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            item {
                HabitFilterChipItem(
                    label = stringResource(R.string.habits_sort_default),
                    selected = !sortAlphabetically,
                    onClick = { onSortAlphabeticallyChange(false) }
                )
            }
            item {
                HabitFilterChipItem(
                    label = stringResource(R.string.habits_sort_alphabetical),
                    selected = sortAlphabetically,
                    onClick = { onSortAlphabeticallyChange(true) }
                )
            }
        }

        if (displayHabits.isEmpty()) {
            HabitsEmptyContent(
                message = stringResource(R.string.habits_empty_list)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                items(displayHabits, key = { it.id }) { habit ->
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
