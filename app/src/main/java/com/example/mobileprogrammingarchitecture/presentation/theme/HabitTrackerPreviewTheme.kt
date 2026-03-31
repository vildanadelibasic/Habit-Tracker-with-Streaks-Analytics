package com.example.mobileprogrammingarchitecture.presentation.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Previews use this so the palette matches the real app (dynamic color off).
 */
@Composable
fun HabitTrackerPreviewTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MobileProgrammingArchitectureTheme(darkTheme = darkTheme, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}
