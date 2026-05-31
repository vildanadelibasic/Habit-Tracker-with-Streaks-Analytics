package com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.components.AuthHeader
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.components.AuthPrimaryButton
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.components.AuthTextField
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.components.PasswordTextField
import com.example.mobileprogrammingarchitecture.presentation.util.AuthValidators
import com.example.mobileprogrammingarchitecture.presentation.view_model.auth.registration.RegistrationUiState

@Composable
fun RegistrationScreen(
    uiState: RegistrationUiState,
    onRegister: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }

    val nameError = AuthValidators.validateFullName(name)
    val emailError = AuthValidators.validateEmail(email)
    val passwordError = AuthValidators.validatePassword(password)
    val confirmError = AuthValidators.validateConfirmPassword(password, confirm)
    val canSubmit = nameError == null && emailError == null && passwordError == null && confirmError == null

    when (uiState) {
        RegistrationUiState.Loading ->
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        is RegistrationUiState.Error ->
            RegisterForm(
                name, email, password, confirm,
                nameError, emailError, passwordError, confirmError,
                canSubmit, uiState.message,
                onNameChange = { name = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onConfirmChange = { confirm = it },
                onRegister = { onRegister(name.trim(), email.trim(), password) },
                onNavigateToLogin = onNavigateToLogin,
                onDismissError = onDismissError,
                modifier = modifier
            )
        RegistrationUiState.Init, RegistrationUiState.Success ->
            RegisterForm(
                name, email, password, confirm,
                nameError, emailError, passwordError, confirmError,
                canSubmit, null,
                onNameChange = { name = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onConfirmChange = { confirm = it },
                onRegister = { onRegister(name.trim(), email.trim(), password) },
                onNavigateToLogin = onNavigateToLogin,
                onDismissError = onDismissError,
                modifier = modifier
            )
    }
}

@Composable
private fun RegisterForm(
    name: String,
    email: String,
    password: String,
    confirm: String,
    nameError: String?,
    emailError: String?,
    passwordError: String?,
    confirmError: String?,
    canSubmit: Boolean,
    bannerError: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmChange: (String) -> Unit,
    onRegister: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        AuthHeader(
            title = stringResource(R.string.register_title),
            subtitle = stringResource(R.string.register_subtitle)
        )
        if (bannerError != null) {
            Text(
                text = bannerError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(onClick = onDismissError) {
                Text(stringResource(R.string.action_dismiss_error))
            }
        }
        AuthTextField(
            value = name,
            onValueChange = onNameChange,
            label = stringResource(R.string.register_name_label),
            error = nameError
        )
        Spacer(Modifier.height(8.dp))
        AuthTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.register_email_label),
            error = emailError,
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(8.dp))
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.register_password_label),
            error = passwordError,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(8.dp))
        PasswordTextField(
            value = confirm,
            onValueChange = onConfirmChange,
            label = stringResource(R.string.register_confirm_password_label),
            error = confirmError,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(24.dp))
        AuthPrimaryButton(
            text = stringResource(R.string.register_submit),
            enabled = canSubmit,
            onClick = onRegister
        )
        TextButton(onClick = onNavigateToLogin, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.register_go_login))
        }
    }
}
