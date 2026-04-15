package com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.components.home.HomeTipRowItem
import com.example.mobileprogrammingarchitecture.presentation.ui.components.profile.ProfileRowItem
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile.util.ProfileRowData

@Composable
fun ProfileScreen(
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val studentTitle = stringResource(R.string.profile_row_student_title)
    val studentSubtitle = stringResource(R.string.profile_row_student_subtitle)
    val versionTitle = stringResource(R.string.profile_row_version_title)
    val versionSubtitle = stringResource(R.string.profile_row_version_subtitle)
    val themeTitle = stringResource(R.string.profile_row_theme_title)
    val themeSubtitle = stringResource(R.string.profile_row_theme_subtitle)
    val aboutNavTitle = stringResource(R.string.profile_nav_about_title)
    val aboutNavSubtitle = stringResource(R.string.profile_nav_about_subtitle)
    val settingsNavTitle = stringResource(R.string.profile_nav_settings_title)
    val settingsNavSubtitle = stringResource(R.string.profile_nav_settings_subtitle)

    val rows = listOf(
        ProfileRowData(1, studentTitle, studentSubtitle),
        ProfileRowData(2, versionTitle, versionSubtitle),
        ProfileRowData(3, themeTitle, themeSubtitle),
        ProfileRowData(4, aboutNavTitle, aboutNavSubtitle, onClick = onOpenAbout),
        ProfileRowData(5, settingsNavTitle, settingsNavSubtitle, onClick = onOpenSettings)
    )

    val tags = stringArrayResource(R.array.profile_tag_labels)

    ProfileScreenContent(
        rows = rows,
        tags = tags,
        modifier = modifier
    )
}

@Composable
private fun ProfileScreenContent(
    rows: List<ProfileRowData>,
    tags: Array<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )
        Text(
            text = stringResource(R.string.profile_tags),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(tags.size) { index ->
                HomeTipRowItem(text = tags[index])
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(rows, key = { it.id }) { row ->
                ProfileRowItem(
                    title = row.title,
                    subtitle = row.subtitle,
                    onClick = row.onClick
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Profile")
@Composable
private fun ProfileScreenPreview() {
    HabitTrackerPreviewTheme {
        ProfileScreen(
            onOpenAbout = {},
            onOpenSettings = {}
        )
    }
}

@Preview(showBackground = true, name = "Profile Dark")
@Composable
private fun ProfileScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        ProfileScreen(
            onOpenAbout = {},
            onOpenSettings = {}
        )
    }
}
