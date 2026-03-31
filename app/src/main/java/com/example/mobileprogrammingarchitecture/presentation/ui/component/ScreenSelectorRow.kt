package com.example.mobileprogrammingarchitecture.presentation.ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme

@Composable
fun ScreenSelectorRow(
    selected: AppScreen,
    onSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val horizontalPadding = dimensionResource(R.dimen.padding_chip_row_horizontal)
    val chipSpacing = dimensionResource(R.dimen.spacing_chips)
    val verticalPad = dimensionResource(R.dimen.padding_nav_vertical)

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = dimensionResource(R.dimen.selector_row_elevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPad
                ),
            horizontalArrangement = Arrangement.spacedBy(chipSpacing)
        ) {
            AppScreen.entries.forEach { screen ->
                FilterChip(
                    selected = selected == screen,
                    onClick = { onSelected(screen) },
                    label = { Text(stringResource(screen.titleRes)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}

@Preview(name = "Bottom / top chips", showBackground = true)
@Composable
private fun ScreenSelectorRowPreview() {
    HabitTrackerPreviewTheme {
        ScreenSelectorRow(
            selected = AppScreen.HOME,
            onSelected = {}
        )
    }
}
