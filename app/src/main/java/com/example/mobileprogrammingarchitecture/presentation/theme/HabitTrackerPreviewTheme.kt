package com.example.mobileprogrammingarchitecture.presentation.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HabitTrackerPreviewTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val preference = if (darkTheme) ThemePreference.Dark else ThemePreference.Light
    MobileProgrammingArchitectureTheme(themePreference = preference, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}
