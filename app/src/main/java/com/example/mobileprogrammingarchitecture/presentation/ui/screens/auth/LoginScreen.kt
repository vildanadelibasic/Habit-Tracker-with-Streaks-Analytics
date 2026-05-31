package com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.mobileprogrammingarchitecture.presentation.view_model.auth.login.LoginUiState

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val emailError = AuthValidators.validateEmail(email)
    val passwordError = AuthValidators.validatePassword(password)
    val canSubmit = emailError == null && passwordError == null

    when (uiState) {
        LoginUiState.Loading ->
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        is LoginUiState.Error -> {
            LoginForm(
                email = email,
                password = password,
                emailError = emailError,
                passwordError = passwordError,
                canSubmit = canSubmit,
                bannerError = uiState.message,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLogin = { onLogin(email.trim(), password) },
                onNavigateToRegister = onNavigateToRegister,
                onDismissError = onDismissError,
                modifier = modifier
            )
        }
        LoginUiState.Init, LoginUiState.Success ->
            LoginForm(
                email = email,
                password = password,
                emailError = emailError,
                passwordError = passwordError,
                canSubmit = canSubmit,
                bannerError = null,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLogin = { onLogin(email.trim(), password) },
                onNavigateToRegister = onNavigateToRegister,
                onDismissError = onDismissError,
                modifier = modifier
            )
    }
}

@Composable
private fun LoginForm(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    canSubmit: Boolean,
    bannerError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        AuthHeader(
            title = stringResource(R.string.login_title),
            subtitle = stringResource(R.string.login_subtitle)
        )
        if (bannerError != null) {
            Text(
                text = bannerError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            TextButton(onClick = onDismissError) {
                Text(stringResource(R.string.action_dismiss_error))
            }
        }
        AuthTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.login_email_label),
            error = emailError,
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(12.dp))
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.login_password_label),
            error = passwordError,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(24.dp))
        AuthPrimaryButton(
            text = stringResource(R.string.login_submit),
            enabled = canSubmit,
            onClick = onLogin
        )
        TextButton(onClick = onNavigateToRegister, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.login_go_register))
        }
    }
}
