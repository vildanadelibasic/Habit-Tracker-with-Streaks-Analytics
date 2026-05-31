package com.example.mobileprogrammingarchitecture.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.about.AboutScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.LoginScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.auth.RegistrationScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habit.AddHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habit.EditHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habit.HabitsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.HomeScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile.ProfileScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.settings.SettingsScreen
import com.example.mobileprogrammingarchitecture.presentation.view_model.about.AboutUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.about.AboutViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.auth.login.LoginUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.auth.login.LoginViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.auth.registration.RegistrationUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.auth.registration.RegistrationViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.HabitUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.HabitViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.add_habit.AddHabitViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit.EditHabitNavigationEvent
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit.EditHabitUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit.EditHabitViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.home.HomeViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.profile.ProfileUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.profile.ProfileViewModel
import com.example.mobileprogrammingarchitecture.presentation.view_model.settings.SettingsUiState
import com.example.mobileprogrammingarchitecture.presentation.view_model.settings.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    snackbarAddedMessage: String,
    snackbarDeletedMessage: String,
    onLogout: () -> Unit
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Register.route) {
            val viewModel: RegistrationViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState) {
                if (uiState is RegistrationUiState.Success) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }

            RegistrationScreen(
                uiState = uiState,
                onRegister = viewModel::register,
                onNavigateToLogin = { navController.popBackStack() },
                onDismissError = viewModel::resetState
            )
        }

        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState) {
                if (uiState is LoginUiState.Success) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
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

        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                uiState = uiState,
                onRefresh = viewModel::refreshHabits,
                onOpenHabits = {
                    navController.navigate(Screen.Habit.route) {
                        launchSingleTop = true
                    }
                },
                onOpenProfile = {
                    navController.navigate(Screen.Profile.route) {
                        launchSingleTop = true
                    }
                },
                onOpenAbout = {
                    navController.navigate(Screen.About.route) {
                        launchSingleTop = true
                    }
                },
                onOpenSettings = {
                    navController.navigate(Screen.Settings.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Habit.route) {
            val viewModel: HabitViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            HabitsScreen(
                uiState = uiState,
                onSearchQueryChange = viewModel::setSearchQuery,
                onListFilterChange = viewModel::setListFilter,
                onSortAlphabeticallyChange = viewModel::setSortAlphabetically,
                onToggleHabitCompleted = viewModel::toggleHabitCompleted,
                onNavigateToDetails = { h ->
                    navController.navigate(Screen.EditHabit.createRoute(h.id))
                }
            )
        }

        composable(Screen.AddHabit.route) {
            val viewModel: AddHabitViewModel = hiltViewModel()
            AddHabitScreen(
                viewModel = viewModel,
                onSaveSuccess = {
                    scope.launch {
                        snackbarHostState.showSnackbar(snackbarAddedMessage)
                    }
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditHabit(0).route,
            arguments = listOf(
                navArgument("habitId") { type = NavType.IntType }
            )
        ) {
            val viewModel: EditHabitViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState) {
                val s = uiState
                if (s is EditHabitUiState.Error && s.popBack) {
                    navController.popBackStack()
                }
            }

            LaunchedEffect(viewModel) {
                viewModel.effects.collect { effect ->
                    when (effect) {
                        EditHabitNavigationEvent.Deleted -> {
                            snackbarHostState.showSnackbar(snackbarDeletedMessage)
                            navController.popBackStack()
                        }
                    }
                }
            }

            when (val s = uiState) {
                is EditHabitUiState.Success ->
                    EditHabitScreen(
                        habit = s.habit,
                        isMutationInProgress = s.isMutationInProgress,
                        onToggleCompleted = viewModel::toggleCompleted,
                        onDelete = viewModel::deleteHabit
                    )
                is EditHabitUiState.Error ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            if (s.popBack && s.message.isEmpty()) {
                                stringResource(R.string.habit_not_found)
                            } else {
                                s.message.ifEmpty { stringResource(R.string.error_unknown) }
                            }
                        )
                    }
                EditHabitUiState.Init,
                EditHabitUiState.Loading ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
            }
        }

        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            when (val s = uiState) {
                is ProfileUiState.Success ->
                    ProfileScreen(
                        completedHabitsCount = s.completedHabits,
                        totalHabitsCount = s.totalHabits,
                        userEmail = s.userEmail,
                        onLogout = {
                            onLogout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onOpenAbout = {
                            navController.navigate(Screen.About.route) {
                                launchSingleTop = true
                            }
                        },
                        onOpenSettings = {
                            navController.navigate(Screen.Settings.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                is ProfileUiState.Error ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(s.message)
                    }
                ProfileUiState.Init,
                ProfileUiState.Loading ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
            }
        }

        composable(Screen.About.route) {
            val viewModel: AboutViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            when (val s = uiState) {
                is AboutUiState.Success ->
                    AboutScreen(
                        habitsInRepository = s.habitsInRepository,
                        completionLogCount = s.completionLogCount,
                        onBack = { navController.popBackStack() }
                    )
                is AboutUiState.Error ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(s.message)
                    }
                AboutUiState.Init,
                AboutUiState.Loading ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
            }
        }

        composable(Screen.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            when (val s = uiState) {
                is SettingsUiState.Success ->
                    SettingsScreen(
                        themePreference = s.themePreference,
                        onThemePreferenceChange = viewModel::setThemePreference,
                        onBack = { navController.popBackStack() }
                    )
                is SettingsUiState.Error ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(s.message)
                    }
                SettingsUiState.Init,
                SettingsUiState.Loading ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
            }
        }
    }
}
