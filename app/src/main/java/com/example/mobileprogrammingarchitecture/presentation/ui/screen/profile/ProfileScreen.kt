package com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.component.ScreenHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile.component.ProfileInfoSection

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenHeader(
            title = "Profile",
            subtitle = "Motivation and account details"
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileInfoSection(
            fullName = "Student Demo",
            email = "student@habit.app",
            role = "Habit Builder"
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    MobileProgrammingArchitectureTheme {
        ProfileScreen()
    }
}
