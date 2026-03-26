package com.example.mobileprogrammingarchitecture.presentation.ui.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme

@Composable
fun HomeStatsCard(
    totalHabits: Int,
    longestStreak: Int,
    remindersEnabledCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Habit Snapshot",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Total habits: $totalHabits")
            Text(text = "Longest streak: $longestStreak days")
            Text(text = "Reminders active: $remindersEnabledCount")
        }
    }
}

@Preview
@Composable
private fun HomeStatsCardPreview() {
    MobileProgrammingArchitectureTheme {
        HomeStatsCard(
            totalHabits = 3,
            longestStreak = 8,
            remindersEnabledCount = 2
        )
    }
}

