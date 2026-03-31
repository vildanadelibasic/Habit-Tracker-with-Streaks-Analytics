package com.example.mobileprogrammingarchitecture.presentation.ui.screen.login.component

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
fun LoginFormCard(
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
                value = email,
                onValueChange = onEmailChange,
                label = { Text(stringResource(R.string.login_email_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = AppShapes.field,
                colors = fieldColors
            )
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(stringResource(R.string.login_password_label)) },
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
                Text(stringResource(R.string.login_submit))
            }
            TextButton(
                onClick = onGoToRegistration,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.login_go_register))
            }
        }
    }
}

@Preview(name = "Login form", showBackground = true)
@Composable
private fun LoginFormCardPreview() {
    HabitTrackerPreviewTheme {
        LoginFormCard(
            email = "student@habit.app",
            password = "secret",
            errorMessage = null,
            canSubmit = true,
            onEmailChange = {},
            onPasswordChange = {},
            onSubmit = {},
            onGoToRegistration = {}
        )
    }
}
