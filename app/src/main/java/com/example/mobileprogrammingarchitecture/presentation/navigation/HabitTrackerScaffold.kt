package com.example.mobileprogrammingarchitecture.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar.BottomBarNavigationComponent
import com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar.BottomBarNavigationItems
import com.example.mobileprogrammingarchitecture.presentation.theme.ThemePreference
import com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits.util.HabitData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerScaffold(
    navController: NavHostController,
    habits: List<HabitData>,
    onHabitsChange: (List<HabitData>) -> Unit,
    themePreference: ThemePreference,
    onThemePreferenceChange: (ThemePreference) -> Unit,
    startDestination: String = Screen.Home.route
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in Screen.bottomNavRoutes()
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
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.cd_add_habit)
                    )
                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomBarNavigationComponent(
                    navController = navController,
                    items = BottomBarNavigationItems.items,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            habits = habits,
            onHabitsChange = onHabitsChange,
            snackbarHostState = snackbarHostState,
            snackbarAddedMessage = snackbarAddedMessage,
            snackbarDeletedMessage = snackbarDeletedMessage,
            themePreference = themePreference,
            onThemePreferenceChange = onThemePreferenceChange
        )
    }
}
