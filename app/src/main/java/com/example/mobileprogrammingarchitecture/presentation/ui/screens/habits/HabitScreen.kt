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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.model.HabitSampleDefaults
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.components.habits.HabitFilterChipItem
import com.example.mobileprogrammingarchitecture.presentation.ui.components.habits.HabitRowItem
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitListFilter
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitsEmptyContent
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitsUiState

@Composable
fun HabitScreen(
    uiState: HabitsUiState,
    onSearchQueryChange: (String) -> Unit,
    onListFilterChange: (HabitListFilter) -> Unit,
    onSortAlphabeticallyChange: (Boolean) -> Unit,
    onToggleHabitCompleted: (Int) -> Unit,
    onNavigateToDetails: (HabitData) -> Unit,
    modifier: Modifier = Modifier
) {
    HabitScreenContent(
        habits = uiState.habits,
        completedCount = uiState.completedCount,
        isWriteInProgress = uiState.isWriteInProgress,
        searchQuery = uiState.searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        listFilter = uiState.listFilter,
        onListFilterChange = onListFilterChange,
        sortAlphabetically = uiState.sortAlphabetically,
        onSortAlphabeticallyChange = onSortAlphabeticallyChange,
        displayHabits = uiState.displayHabits,
        onToggleHabitCompleted = onToggleHabitCompleted,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@Composable
private fun HabitScreenContent(
    habits: List<HabitData>,
    completedCount: Int,
    isWriteInProgress: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    listFilter: HabitListFilter,
    onListFilterChange: (HabitListFilter) -> Unit,
    sortAlphabetically: Boolean,
    onSortAlphabeticallyChange: (Boolean) -> Unit,
    displayHabits: List<HabitData>,
    onToggleHabitCompleted: (Int) -> Unit,
    onNavigateToDetails: (HabitData) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        if (isWriteInProgress) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            Text(
                text = stringResource(R.string.habits_write_in_progress),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }
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
                            onToggleHabitCompleted(habit.id)
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
    val sample = HabitSampleDefaults.initial
    val filtered = sample.filter { it.title.contains("", ignoreCase = true) }
    HabitTrackerPreviewTheme {
        HabitScreen(
            uiState = HabitsUiState(
                habits = sample,
                searchQuery = "",
                listFilter = HabitListFilter.All,
                sortAlphabetically = false,
                displayHabits = filtered,
                completedCount = sample.count { it.isCompleted },
                isWriteInProgress = false
            ),
            onSearchQueryChange = {},
            onListFilterChange = {},
            onSortAlphabeticallyChange = {},
            onToggleHabitCompleted = {},
            onNavigateToDetails = {}
        )
    }
}

@Preview(showBackground = true, name = "Habits empty filter")
@Composable
private fun HabitScreenEmptyPreview() {
    HabitTrackerPreviewTheme {
        HabitScreen(
            uiState = HabitsUiState(),
            onSearchQueryChange = {},
            onListFilterChange = {},
            onSortAlphabeticallyChange = {},
            onToggleHabitCompleted = {},
            onNavigateToDetails = {}
        )
    }
}
