package com.example.mobileprogrammingarchitecture.presentation.ui.screen.registration.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.AppShapes
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme

@Composable
fun RegistrationFormCard(
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
    val cardPadding = dimensionResource(R.dimen.padding_card)
    val fieldSpacing = dimensionResource(R.dimen.spacing_form_fields)
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
    )

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.card,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(cardPadding),
            verticalArrangement = Arrangement.spacedBy(fieldSpacing)
        ) {
            OutlinedTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                label = { Text(stringResource(R.string.register_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = AppShapes.field,
                colors = fieldColors
            )
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text(stringResource(R.string.register_email_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = AppShapes.field,
                colors = fieldColors
            )
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(stringResource(R.string.register_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = AppShapes.field,
                colors = fieldColors
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text(stringResource(R.string.register_confirm_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = AppShapes.field,
                colors = fieldColors
            )
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = onSubmit,
                enabled = canSubmit,
                shape = AppShapes.button,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(stringResource(R.string.register_submit))
            }
            TextButton(
                onClick = onGoToLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.register_go_login))
            }
        }
    }
}

@Preview(name = "Registration form", showBackground = true)
@Composable
private fun RegistrationFormCardPreview() {
    HabitTrackerPreviewTheme {
        RegistrationFormCard(
            fullName = "Student Demo",
            email = "student@habit.app",
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
