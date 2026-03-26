package com.example.mobileprogrammingarchitecture.presentation.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.home.component.HomeStatsCard

@Composable
fun HomeScreen(
    totalHabits: Int,
    longestStreak: Int,
    remindersEnabledCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenHeader(
            title = "Habit Tracker",
            subtitle = "Build consistency with simple daily actions."
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomeStatsCard(
            totalHabits = totalHabits,
            longestStreak = longestStreak,
            remindersEnabledCount = remindersEnabledCount
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MobileProgrammingArchitectureTheme {
        HomeScreen(
            totalHabits = 4,
            longestStreak = 9,
            remindersEnabledCount = 3
        )
    }
}