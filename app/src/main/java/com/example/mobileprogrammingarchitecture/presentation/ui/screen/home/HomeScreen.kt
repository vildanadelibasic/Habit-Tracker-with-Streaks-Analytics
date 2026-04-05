package com.example.mobileprogrammingarchitecture.presentation.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.home.component.HomeStatsCard
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.home.component.HabitItem

@Composable
fun HomeScreen(
    totalHabits: Int,
    longestStreak: Int,
    remindersEnabledCount: Int,
    modifier: Modifier = Modifier
) {
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val sectionLarge = dimensionResource(R.dimen.spacing_section_large)

    val habits = listOf(
        "Drink water",
        "Workout",
        "Read book"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(screenPadding)
    ) {
        ScreenHeader(
            title = stringResource(R.string.home_title),
            subtitle = stringResource(R.string.home_subtitle)
        )

        Spacer(modifier = Modifier.height(sectionLarge))

        HomeStatsCard(
            totalHabits = totalHabits,
            longestStreak = longestStreak,
            remindersEnabledCount = remindersEnabledCount
        )

        Spacer(modifier = Modifier.height(sectionLarge))

        LazyColumn {
            items(habits) { habit ->
                HabitItem(
                    name = habit,
                    onClick = {
                        // kasnije navigation ide ovdje
                    }
                )
            }
        }
    }
}

@Preview(name = "Home", showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HabitTrackerPreviewTheme {
        HomeScreen(
            totalHabits = 4,
            longestStreak = 9,
            remindersEnabledCount = 3
        )
    }
}

@Preview(name = "Home — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        HomeScreen(
            totalHabits = 2,
            longestStreak = 5,
            remindersEnabledCount = 1
        )
    }
}