package com.example.mobileprogrammingarchitecture.presentation.ui.screen.habits

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.preview.PreviewSampleData
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.habits.component.HabitListItem

@Composable
fun HabitsScreen(
    habits: List<Habit>,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val sectionMedium = dimensionResource(R.dimen.spacing_section_medium)
    val sectionSmall = dimensionResource(R.dimen.spacing_section_small)
    val chipSpacing = dimensionResource(R.dimen.spacing_chips)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(screenPadding),
        verticalArrangement = Arrangement.spacedBy(sectionMedium)
    ) {
        ScreenHeader(
            title = stringResource(R.string.habits_title),
            subtitle = stringResource(R.string.habits_subtitle)
        )

        FilledTonalButton(
            onClick = onClearAll,
            modifier = Modifier.fillMaxWidth(0.55f)
        ) {
            Text(stringResource(R.string.habits_clear_all))
        }

        if (habits.isEmpty()) {
            Spacer(modifier = Modifier.height(sectionSmall))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.TaskAlt,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )

                Text(
                    text = stringResource(R.string.habits_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(chipSpacing)
            ) {
                items(habits, key = { it.id }) { habit ->
                    HabitListItem(
                        habit = habit,
                        onClick = {
                            println("Clicked: ${habit.name}")
                        }
                    )
                }
            }
        }
    }
}

@Preview(name = "Habits — with data", showBackground = true)
@Composable
private fun HabitsScreenPreview() {
    HabitTrackerPreviewTheme {
        HabitsScreen(
            habits = PreviewSampleData.habits,
            onClearAll = {}
        )
    }
}

@Preview(name = "Habits — empty", showBackground = true)
@Composable
private fun HabitsScreenEmptyPreview() {
    HabitTrackerPreviewTheme {
        HabitsScreen(
            habits = emptyList(),
            onClearAll = {}
        )
    }
}

@Preview(name = "Habits — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HabitsScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        HabitsScreen(
            habits = PreviewSampleData.habits,
            onClearAll = {}
        )
    }
}