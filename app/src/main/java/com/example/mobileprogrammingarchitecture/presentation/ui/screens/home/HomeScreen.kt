package com.example.mobileprogrammingarchitecture.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.data.model.HabitSampleDefaults
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.components.home.HomeShortcutItem
import com.example.mobileprogrammingarchitecture.presentation.ui.components.home.HomeTipRowItem
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.util.HomeProgressSection
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.util.HomeShortcut
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.util.ShortcutTarget
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRefresh: () -> Unit,
    onOpenHabits: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (val s = uiState) {
        is HomeUiState.Success -> {
            val habits = s.habits
            val completedCount = remember(habits) { habits.count { it.isCompleted } }
            val progress = remember(habits, completedCount) {
                if (habits.isEmpty()) 0f else completedCount.toFloat() / habits.size
            }

            val habitsShortcutTitle = stringResource(R.string.home_shortcut_habits)
            val profileShortcutTitle = stringResource(R.string.home_shortcut_profile)
            val aboutShortcutTitle = stringResource(R.string.home_shortcut_about)
            val settingsShortcutTitle = stringResource(R.string.home_shortcut_settings)
            val shortcuts = remember(
                habitsShortcutTitle,
                profileShortcutTitle,
                aboutShortcutTitle,
                settingsShortcutTitle
            ) {
                listOf(
                    HomeShortcut(1, habitsShortcutTitle, ShortcutTarget.Habits),
                    HomeShortcut(2, profileShortcutTitle, ShortcutTarget.Profile),
                    HomeShortcut(3, aboutShortcutTitle, ShortcutTarget.About),
                    HomeShortcut(4, settingsShortcutTitle, ShortcutTarget.Settings)
                )
            }

            val tips = stringArrayResource(R.array.home_tips)

            HomeScreenContent(
                isRefreshing = s.isRefreshing,
                onRefresh = onRefresh,
                completedCount = completedCount,
                totalHabitCount = habits.size,
                progress = progress,
                shortcuts = shortcuts,
                tips = tips,
                onOpenHabits = onOpenHabits,
                onOpenProfile = onOpenProfile,
                onOpenAbout = onOpenAbout,
                onOpenSettings = onOpenSettings,
                modifier = modifier
            )
        }
        HomeUiState.Init,
        HomeUiState.Loading ->
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        is HomeUiState.Error ->
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(s.message, style = MaterialTheme.typography.bodyLarge)
            }
    }
}

@Composable
private fun HomeScreenContent(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    completedCount: Int,
    totalHabitCount: Int,
    progress: Float,
    shortcuts: List<HomeShortcut>,
    tips: Array<String>,
    onOpenHabits: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.home_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.home_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (isRefreshing) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(28.dp),
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(
                    onClick = onRefresh,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = stringResource(R.string.cd_home_sync)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        HomeProgressSection(
            completedCount = completedCount,
            totalCount = totalHabitCount,
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
                val icon: ImageVector = when (shortcut.target) {
                    ShortcutTarget.Habits -> Icons.Outlined.List
                    ShortcutTarget.Profile -> Icons.Outlined.FavoriteBorder
                    ShortcutTarget.About -> Icons.Outlined.Info
                    ShortcutTarget.Settings -> Icons.Outlined.Settings
                }
                HomeShortcutItem(
                    title = shortcut.title,
                    icon = icon,
                    onClick = {
                        when (shortcut.target) {
                            ShortcutTarget.Habits -> onOpenHabits()
                            ShortcutTarget.Profile -> onOpenProfile()
                            ShortcutTarget.About -> onOpenAbout()
                            ShortcutTarget.Settings -> onOpenSettings()
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

@Preview(showBackground = true, name = "Home")
@Composable
private fun HomeScreenPreview() {
    HabitTrackerPreviewTheme {
        HomeScreen(
            uiState = HomeUiState.Success(
                habits = HabitSampleDefaults.initial,
                isRefreshing = false
            ),
            onRefresh = {},
            onOpenHabits = {},
            onOpenProfile = {},
            onOpenAbout = {},
            onOpenSettings = {}
        )
    }
}

@Preview(showBackground = true, name = "Home Dark")
@Composable
private fun HomeScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        HomeScreen(
            uiState = HomeUiState.Success(
                habits = HabitSampleDefaults.initial,
                isRefreshing = false
            ),
            onRefresh = {},
            onOpenHabits = {},
            onOpenProfile = {},
            onOpenAbout = {},
            onOpenSettings = {}
        )
    }
}
