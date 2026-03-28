package com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile

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
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile.component.ProfileInfoSection

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val sectionLarge = dimensionResource(R.dimen.spacing_section_large)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(screenPadding)
    ) {
        ScreenHeader(
            title = stringResource(R.string.profile_title),
            subtitle = stringResource(R.string.profile_subtitle)
        )
        Spacer(modifier = Modifier.height(sectionLarge))
        ProfileInfoSection(
            fullName = stringResource(R.string.profile_demo_name),
            email = stringResource(R.string.profile_demo_email),
            role = stringResource(R.string.profile_demo_role)
        )
    }
}

@Preview(name = "Profile", showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    HabitTrackerPreviewTheme {
        ProfileScreen()
    }
}

@Preview(name = "Profile — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        ProfileScreen()
    }
}
