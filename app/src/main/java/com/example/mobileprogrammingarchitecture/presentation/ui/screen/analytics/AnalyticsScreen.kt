package com.example.mobileprogrammingarchitecture.presentation.ui.screen.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.analytics.component.AnalyticsCard

@Composable
fun AnalyticsScreen(
    habits: List<Habit>,
    modifier: Modifier = Modifier
) {
    val averageFrequency = if (habits.isEmpty()) 0 else habits.sumOf { it.frequencyPerWeek } / habits.size
    val completionEstimate = (averageFrequency * 100 / 7).coerceIn(0, 100)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenHeader(
            title = "Analytics",
            subtitle = "Simple visual stats for your weekly habit plan."
        )
        AnalyticsCard(
            completionEstimate = completionEstimate,
            averageFrequency = averageFrequency
        )
    }
}
