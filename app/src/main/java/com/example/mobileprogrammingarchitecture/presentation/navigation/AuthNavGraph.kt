package com.example.mobileprogrammingarchitecture.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.LoginScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.RegisterScreen
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.LoginViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.RegisterViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.LoginUiState
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.RegisterUiState

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    onAuthenticated: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState) {
                if (uiState is LoginUiState.Success) {
                    onAuthenticated()
                }
            }

            LoginScreen(
                uiState = uiState,
                onLogin = viewModel::login,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) {
                        launchSingleTop = true
                    }
                },
                onDismissError = viewModel::resetState
            )
        }
        composable(Screen.Register.route) {
            val viewModel: RegisterViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState) {
                if (uiState is RegisterUiState.Success) {
                    onAuthenticated()
                }
            }

            RegisterScreen(
                uiState = uiState,
                onRegister = viewModel::register,
                onNavigateToLogin = { navController.popBackStack() },
                onDismissError = viewModel::resetState
            )
        }
    }
}

@Composable
fun AuthLoadingGate(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
