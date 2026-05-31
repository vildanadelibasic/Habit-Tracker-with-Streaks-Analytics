package com.example.mobileprogrammingarchitecture.presentation.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.domain.data.ThemePreference
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.settings.components.SettingsThemeChipItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    themePreference: ThemePreference,
    onThemePreferenceChange: (ThemePreference) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScreenContent(
        themePreference = themePreference,
        onThemePreferenceChange = onThemePreferenceChange,
        onBack = onBack,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenContent(
    themePreference: ThemePreference,
    onThemePreferenceChange: (ThemePreference) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = stringResource(R.string.settings_theme_section),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            SettingsThemeChipItem(
                label = stringResource(R.string.settings_theme_system),
                selected = themePreference == ThemePreference.System,
                onClick = { onThemePreferenceChange(ThemePreference.System) }
            )
            SettingsThemeChipItem(
                label = stringResource(R.string.settings_theme_light),
                selected = themePreference == ThemePreference.Light,
                onClick = { onThemePreferenceChange(ThemePreference.Light) }
            )
            SettingsThemeChipItem(
                label = stringResource(R.string.settings_theme_dark),
                selected = themePreference == ThemePreference.Dark,
                onClick = { onThemePreferenceChange(ThemePreference.Dark) },
                modifier = Modifier.padding(bottom = 0.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    HabitTrackerPreviewTheme {
        SettingsScreen(
            themePreference = ThemePreference.System,
            onThemePreferenceChange = {},
            onBack = {}
        )
    }
}
