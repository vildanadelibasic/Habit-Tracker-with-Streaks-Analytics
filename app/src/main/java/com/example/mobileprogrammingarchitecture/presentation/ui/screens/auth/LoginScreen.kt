package com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.mobileprogrammingarchitecture.presentation.util.Validation
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.LoginUiState

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

    val emailError = Validation.validateEmail(email)
    val passwordError = Validation.validatePassword(password)
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
        Text(stringResource(R.string.login_title), style = MaterialTheme.typography.headlineMedium)
        Text(
            stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
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
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(R.string.login_email_label)) },
            isError = emailError != null,
            supportingText = emailError?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.login_password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = passwordError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onLogin, enabled = canSubmit, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.login_submit))
        }
        TextButton(onClick = onNavigateToRegister, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.login_go_register))
        }
    }
}
