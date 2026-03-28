package com.example.mobileprogrammingarchitecture.presentation.ui.screen.analytics

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.preview.PreviewSampleData
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.analytics.component.AnalyticsCard

@Composable
fun AnalyticsScreen(
    habits: List<Habit>,
    modifier: Modifier = Modifier
) {
    val averageFrequency = if (habits.isEmpty()) 0 else habits.sumOf { it.frequencyPerWeek } / habits.size
    val completionEstimate = (averageFrequency * 100 / 7).coerceIn(0, 100)
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val sectionLarge = dimensionResource(R.dimen.spacing_section_large)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(screenPadding)
    ) {
        ScreenHeader(
            title = stringResource(R.string.analytics_title),
            subtitle = stringResource(R.string.analytics_subtitle)
        )
        Spacer(modifier = Modifier.height(sectionLarge))
        AnalyticsCard(
            completionEstimate = completionEstimate,
            averageFrequency = averageFrequency
        )
    }
}

@Preview(name = "Analytics", showBackground = true)
@Composable
private fun AnalyticsScreenPreview() {
    HabitTrackerPreviewTheme {
        AnalyticsScreen(habits = PreviewSampleData.habits)
    }
}

@Preview(name = "Analytics — empty", showBackground = true)
@Composable
private fun AnalyticsScreenEmptyPreview() {
    HabitTrackerPreviewTheme {
        AnalyticsScreen(habits = emptyList())
    }
}

@Preview(name = "Analytics — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AnalyticsScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        AnalyticsScreen(habits = PreviewSampleData.habits)
    }
}
