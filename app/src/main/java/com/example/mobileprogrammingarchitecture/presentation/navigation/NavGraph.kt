package com.example.mobileprogrammingarchitecture.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobileprogrammingarchitecture.presentation.di.LocalHabitTrackerViewModelFactory
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.about.AboutScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.AddHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitDetailsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.HomeScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile.ProfileScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.settings.SettingsScreen
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.AboutViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.AddHabitViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitDetailsEffect
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitDetailsViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitDetailsViewModelFactory
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitsViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HomeViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.ProfileViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    snackbarAddedMessage: String,
    snackbarDeletedMessage: String
) {
    val viewModelFactory = LocalHabitTrackerViewModelFactory.current
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                habits = uiState.habits,
                isRefreshing = uiState.isRefreshing,
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
            val viewModel: HabitsViewModel = viewModel(factory = viewModelFactory)
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
            val viewModel: AddHabitViewModel = viewModel(factory = viewModelFactory)
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
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: return@composable
            val factory = remember(id) {
                HabitDetailsViewModelFactory(
                    habitId = id,
                    habitRepository = viewModelFactory.habitRepository
                )
            }
            val viewModel: HabitDetailsViewModel = viewModel(key = "habit_detail_$id", factory = factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState.hasResolved, uiState.habit, uiState.wasEverPresent) {
                if (uiState.hasResolved && uiState.habit == null && !uiState.wasEverPresent) {
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

            val habit = uiState.habit
            if (habit != null) {
                HabitDetailsScreen(
                    habit = habit,
                    isMutationInProgress = uiState.isMutationInProgress,
                    onToggleCompleted = viewModel::toggleCompleted,
                    onDelete = viewModel::deleteHabit
                )
            }
        }
        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ProfileScreen(
                completedHabitsCount = uiState.completedHabits,
                totalHabitsCount = uiState.totalHabits,
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
        composable(Screen.About.route) {
            val viewModel: AboutViewModel = viewModel(factory = viewModelFactory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            AboutScreen(
                habitsInRepository = uiState.habitsInRepository,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            val viewModel: SettingsViewModel = viewModel(factory = viewModelFactory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SettingsScreen(
                themePreference = uiState.themePreference,
                onThemePreferenceChange = viewModel::setThemePreference,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
