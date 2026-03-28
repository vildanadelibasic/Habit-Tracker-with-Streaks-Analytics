package com.example.mobileprogrammingarchitecture.presentation.ui.screen.registration

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
import com.example.mobileprogrammingarchitecture.presentation.ui.screen.registration.component.RegistrationFormCard

@Composable
fun RegistrationScreen(
    fullName: String,
    email: String,
    password: String,
    confirmPassword: String,
    errorMessage: String?,
    canSubmit: Boolean,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onGoToLogin: () -> Unit,
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
            title = stringResource(R.string.register_title),
            subtitle = stringResource(R.string.register_subtitle)
        )
        Spacer(modifier = Modifier.height(sectionLarge))
        RegistrationFormCard(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            errorMessage = errorMessage,
            canSubmit = canSubmit,
            onFullNameChange = onFullNameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onConfirmPasswordChange = onConfirmPasswordChange,
            onSubmit = onSubmit,
            onGoToLogin = onGoToLogin
        )
    }
}

@Preview(name = "Registration", showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    HabitTrackerPreviewTheme {
        RegistrationScreen(
            fullName = "",
            email = "",
            password = "",
            confirmPassword = "",
            errorMessage = null,
            canSubmit = false,
            onFullNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSubmit = {},
            onGoToLogin = {}
        )
    }
}

@Preview(name = "Registration — error", showBackground = true)
@Composable
private fun RegistrationScreenErrorPreview() {
    HabitTrackerPreviewTheme {
        RegistrationScreen(
            fullName = "A",
            email = "x",
            password = "123",
            confirmPassword = "456",
            errorMessage = "Passwords do not match.",
            canSubmit = true,
            onFullNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSubmit = {},
            onGoToLogin = {}
        )
    }
}

@Preview(name = "Registration — dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RegistrationScreenDarkPreview() {
    HabitTrackerPreviewTheme(darkTheme = true) {
        RegistrationScreen(
            fullName = "Demo User",
            email = "demo@test.com",
            password = "123456",
            confirmPassword = "123456",
            errorMessage = null,
            canSubmit = true,
            onFullNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSubmit = {},
            onGoToLogin = {}
        )
    }
}
