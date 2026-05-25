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
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.about.AboutScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.AddHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitDetailsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.HomeScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile.ProfileScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.settings.SettingsScreen
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.AboutViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.AddHabitViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitDetailsViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitsViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HomeViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.ProfileViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.SettingsViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.AboutUiState
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.HabitDetailsEffect
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.HabitDetailsUiState
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.HomeUiState
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.ProfileUiState
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.SettingsUiState
import com.example.mobileprogrammingarchitecture.R
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
            val viewModel: HabitsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            HabitScreen(
                uiState = uiState,
                onSearchQueryChange = viewModel::setSearchQuery,
                onListFilterChange = viewModel::setListFilter,
                onSortAlphabeticallyChange = viewModel::setSortAlphabetically,
                onToggleHabitCompleted = viewModel::toggleHabitCompleted,
                onNavigateToDetails = { h ->
                    navController.navigate(Screen.HabitDetails.createRoute(h.id, h.title))
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
            route = Screen.HabitDetails.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }
            )
        ) {
            val viewModel: HabitDetailsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState) {
                val s = uiState
                if (s is HabitDetailsUiState.Error && s.popBack) {
                    navController.popBackStack()
                }
            }

            LaunchedEffect(viewModel) {
                viewModel.effects.collect { effect ->
                    when (effect) {
                        HabitDetailsEffect.Deleted -> {
                            snackbarHostState.showSnackbar(snackbarDeletedMessage)
                            navController.popBackStack()
                        }
                    }
                }
            }

            when (val s = uiState) {
                is HabitDetailsUiState.Success ->
                    HabitDetailsScreen(
                        habit = s.habit,
                        isMutationInProgress = s.isMutationInProgress,
                        onToggleCompleted = viewModel::toggleCompleted,
                        onDelete = viewModel::deleteHabit
                    )
                is HabitDetailsUiState.Error ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            if (s.popBack && s.message.isEmpty()) {
                                stringResource(R.string.habit_not_found)
                            } else {
                                s.message.ifEmpty { stringResource(R.string.error_unknown) }
                            }
                        )
                    }
                HabitDetailsUiState.Init,
                HabitDetailsUiState.Loading ->
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
                        onLogout = onLogout,
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
