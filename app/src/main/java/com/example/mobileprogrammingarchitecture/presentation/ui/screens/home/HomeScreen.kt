package com.example.mobileprogrammingarchitecture.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.components.home.HomeShortcutItem
import com.example.mobileprogrammingarchitecture.presentation.ui.components.home.HomeTipRowItem
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitSampleDefaults
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.util.HomeShortcut
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.util.ShortcutTarget

@Composable
fun HomeScreen(
    habits: List<HabitData>,
    onOpenHabits: () -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = remember(habits) { habits.count { it.isCompleted } }
    val progress = remember(habits, completedCount) {
        if (habits.isEmpty()) 0f else completedCount.toFloat() / habits.size
    }

    val habitsShortcutTitle = stringResource(R.string.home_shortcut_habits)
    val profileShortcutTitle = stringResource(R.string.home_shortcut_profile)
    val shortcuts = remember(habitsShortcutTitle, profileShortcutTitle) {
        listOf(
            HomeShortcut(1, habitsShortcutTitle, ShortcutTarget.Habits),
            HomeShortcut(2, profileShortcutTitle, ShortcutTarget.Profile)
        )
    }

    val tips = stringArrayResource(R.array.home_tips)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        HomeProgressSection(
            completedCount = completedCount,
            totalCount = habits.size,
            progress = progress
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.home_quick_access),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(shortcuts, key = { it.id }) { shortcut ->
                HomeShortcutItem(
                    title = shortcut.title,
                    icon = when (shortcut.target) {
                        ShortcutTarget.Habits -> Icons.Outlined.List
                        ShortcutTarget.Profile -> Icons.Outlined.FavoriteBorder
                    },
                    onClick = {
                        when (shortcut.target) {
                            ShortcutTarget.Habits -> onOpenHabits()
                            ShortcutTarget.Profile -> onOpenProfile()
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.home_tips_section),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(tips.size) { index ->
                HomeTipRowItem(text = tips[index])
            }
        }
    }
}

@Composable
private fun HomeProgressSection(
    completedCount: Int,
    totalCount: Int,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.home_progress_format, completedCount, totalCount),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(8.dp)
        )
    }
}

@Preview(showBackground = true, name = "Home")
@Composable
private fun HomeScreenPreview() {
    HabitTrackerPreviewTheme {
        HomeScreen(
            habits = HabitSampleDefaults.initial,
            onOpenHabits = {},
            onOpenProfile = {}
        )
    }
}

@Preview(showBackground = true, name = "Home Dark")
@Composable
private fun HomeScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        HomeScreen(
            habits = HabitSampleDefaults.initial,
            onOpenHabits = {},
            onOpenProfile = {}
        )
    }
}
