package com.example.mobileprogrammingarchitecture.presentation.ui.screen.login

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
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.login.component.LoginFormCard

@Composable
fun LoginScreen(
    email: String,
    password: String,
    errorMessage: String?,
    canSubmit: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onGoToRegistration: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenPadding = dimensionResource(R.dimen.padding_screen)
    val sectionLarge = dimensionResource(R.dimen.spacing_section_large)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(screenPadding)
    ) {
        ScreenHeader(
            title = stringResource(R.string.login_title),
            subtitle = stringResource(R.string.login_subtitle)
        )
        Spacer(modifier = Modifier.height(sectionLarge))
        LoginFormCard(
            email = email,
            password = password,
            errorMessage = errorMessage,
            canSubmit = canSubmit,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onSubmit = onSubmit,
            onGoToRegistration = onGoToRegistration
        )
    }
}

@Preview(name = "Login", showBackground = true)
@Composable
private fun LoginScreenPreview() {
    HabitTrackerPreviewTheme {
        LoginScreen(
            email = "",
            password = "",
            errorMessage = null,
            canSubmit = false,
            onEmailChange = {},
            onPasswordChange = {},
            onSubmit = {},
            onGoToRegistration = {}
        )
    }
}

@Preview(name = "Login — with error", showBackground = true)
@Composable
private fun LoginScreenErrorPreview() {
    HabitTrackerPreviewTheme {
        LoginScreen(
            email = "bad",
            password = "",
            errorMessage = "Enter a valid email address.",
            canSubmit = true,
            onEmailChange = {},
            onPasswordChange = {},
            onSubmit = {},
            onGoToRegistration = {}
        )
    }
}

@Preview(name = "Login — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        LoginScreen(
            email = "demo@test.com",
            password = "123456",
            errorMessage = null,
            canSubmit = true,
            onEmailChange = {},
            onPasswordChange = {},
            onSubmit = {},
            onGoToRegistration = {}
        )
    }
}
