package com.example.mobileprogrammingarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobileprogrammingarchitecture.presentation.navigation.Screen
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.AddHabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitDetailsScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.HabitScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitData
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitSampleDefaults
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.HomeScreen
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileProgrammingArchitectureTheme {
                HabitTrackerRoot()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerRoot() {
    var habits by remember { mutableStateOf(HabitSampleDefaults.initial) }
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute == Screen.Home.route ||
        currentRoute == Screen.Habit.route ||
        currentRoute == Screen.Profile.route

    val showFab = currentRoute == Screen.Habit.route

    val snackbarAddedMessage = stringResource(R.string.snackbar_habit_added)
    val snackbarDeletedMessage = stringResource(R.string.snackbar_habit_deleted)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddHabit.route) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.cd_add_habit)
                    )
                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_home)) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.Habit.route,
                        onClick = {
                            navController.navigate(Screen.Habit.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Outlined.List, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_habits)) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_profile)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
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
                    }
                )
            }
            composable(Screen.Habit.route) {
                HabitScreen(
                    habits = habits,
                    onHabitsChange = { habits = it },
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
                        habits = habits + newHabit
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
                        habits = habits.map { h ->
                            if (h.id == id) h.copy(isCompleted = !h.isCompleted) else h
                        }
                    },
                    onDelete = {
                        habits = habits.filter { it.id != id }
                        scope.launch {
                            snackbarHostState.showSnackbar(snackbarDeletedMessage)
                        }
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}
