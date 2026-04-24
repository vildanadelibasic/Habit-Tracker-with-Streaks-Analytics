package com.example.mobileprogrammingarchitecture.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobileprogrammingarchitecture.presentation.theme.ThemePreference
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.about.AboutScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.AddHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitDetailsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.HomeScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile.ProfileScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    habits: List<HabitData>,
    onHabitsChange: (List<HabitData>) -> Unit,
    snackbarHostState: SnackbarHostState,
    snackbarAddedMessage: String,
    snackbarDeletedMessage: String,
    themePreference: ThemePreference,
    onThemePreferenceChange: (ThemePreference) -> Unit
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                habits = habits,
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
            HabitScreen(
                habits = habits,
                onHabitsChange = onHabitsChange,
                onNavigateToDetails = { h ->
                    navController.navigate(Screen.HabitDetails.createRoute(h.id, h.title))
                }
            )
        }
        composable(Screen.AddHabit.route) {
            val nextId = (habits.maxOfOrNull { it.id } ?: 0) + 1
            AddHabitScreen(
                nextId = nextId,
                onSave = { newHabit: HabitData ->
                    onHabitsChange(habits + newHabit)
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
            val habit = habits.find { it.id == id }
            if (habit == null) {
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
                return@composable
            }
            HabitDetailsScreen(
                habit = habit,
                onToggleCompleted = {
                    onHabitsChange(
                        habits.map { h ->
                            if (h.id == id) h.copy(isCompleted = !h.isCompleted) else h
                        }
                    )
                },
                onDelete = {
                    onHabitsChange(habits.filter { it.id != id })
                    scope.launch {
                        snackbarHostState.showSnackbar(snackbarDeletedMessage)
                    }
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
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
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                themePreference = themePreference,
                onThemePreferenceChange = onThemePreferenceChange,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
