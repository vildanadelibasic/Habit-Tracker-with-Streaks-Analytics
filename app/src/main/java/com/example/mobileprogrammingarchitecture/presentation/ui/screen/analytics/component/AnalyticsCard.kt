package com.example.mobileprogrammingarchitecture.presentation.ui.screen.analytics.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.AppShapes
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme

@Composable
fun AnalyticsCard(
    completionEstimate: Int,
    averageFrequency: Int,
    modifier: Modifier = Modifier
) {
    val cardPadding = dimensionResource(R.dimen.padding_card)

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.card,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(cardPadding)) {
            Text(
                text = stringResource(R.string.analytics_weekly_consistency, completionEstimate),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.analytics_avg_frequency, averageFrequency),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_section_small))
            )
            Text(
                text = stringResource(R.string.analytics_tip),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_section_medium))
            )
        }
    }
}

@Preview(name = "Analytics card", showBackground = true)
@Composable
private fun AnalyticsCardPreview() {
    HabitTrackerPreviewTheme {
        AnalyticsCard(completionEstimate = 71, averageFrequency = 5)
    }
}
