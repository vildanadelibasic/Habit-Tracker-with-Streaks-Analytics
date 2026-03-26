package com.example.mobileprogrammingarchitecture.presentation.ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen

@Composable
fun ScreenSelectorRow(
    selected: AppScreen,
    onSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppScreen.entries.forEach { screen ->
            FilterChip(
                selected = selected == screen,
                onClick = { onSelected(screen) },
                label = { Text(screen.title) }
            )
        }
    }
}
