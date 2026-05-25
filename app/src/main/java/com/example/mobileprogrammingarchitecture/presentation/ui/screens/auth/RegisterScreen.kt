package com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.RegisterUiState

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onRegister: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }

    val nameError = Validation.validateName(name)
    val emailError = Validation.validateEmail(email)
    val passwordError = Validation.validatePassword(password)
    val confirmError = Validation.validatePasswordMatch(password, confirm)
    val canSubmit = nameError == null && emailError == null && passwordError == null && confirmError == null

    when (uiState) {
        RegisterUiState.Loading ->
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        is RegisterUiState.Error ->
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
        RegisterUiState.Init, RegisterUiState.Success ->
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
        Text(stringResource(R.string.register_title), style = MaterialTheme.typography.headlineMedium)
        Text(
            stringResource(R.string.register_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
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
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.register_name_label)) },
            isError = nameError != null,
            supportingText = nameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(R.string.register_email_label)) },
            isError = emailError != null,
            supportingText = emailError?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.register_password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = passwordError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = confirm,
            onValueChange = onConfirmChange,
            label = { Text(stringResource(R.string.register_confirm_password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmError != null,
            supportingText = confirmError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onRegister, enabled = canSubmit, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.register_submit))
        }
        TextButton(onClick = onNavigateToLogin, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.register_go_login))
        }
    }
}
