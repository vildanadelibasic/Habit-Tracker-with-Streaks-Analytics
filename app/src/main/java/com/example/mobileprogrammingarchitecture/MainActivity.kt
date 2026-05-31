package com.example.mobileprogrammingarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobileprogrammingarchitecture.domain.data.ThemePreference
import com.example.mobileprogrammingarchitecture.domain.repository.AuthRepository
import com.example.mobileprogrammingarchitecture.domain.repository.UserPreferencesRepository
import com.example.mobileprogrammingarchitecture.presentation.navigation.NavGraph
import com.example.mobileprogrammingarchitecture.presentation.navigation.Screen
import com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar.BottomBarDestination
import com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar.BottomBarNavigationComponent
import com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar.BottomBarNavigationItems
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreference by userPreferencesRepository.observeThemePreference()
                .collectAsStateWithLifecycle(initialValue = ThemePreference.System)
            val isLoggedIn by authRepository.observeIsLoggedIn()
                .collectAsStateWithLifecycle(initialValue = null)

            MobileProgrammingArchitectureTheme(themePreference = themePreference) {
                when (isLoggedIn) {
                    null -> AuthLoadingGate()
                    else -> {
                        val navController = rememberNavController()

                        LaunchedEffect(isLoggedIn) {
                            if (isLoggedIn == true) {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }

                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        val showBottomBar = Screen.isBottomBarRoute(currentRoute) && isLoggedIn == true

                        BottomNavScaffold(
                            navController = navController,
                            startDestination = Screen.Login.route,
                            showBottomBar = showBottomBar,
                            showFab = currentRoute == Screen.Habit.route && isLoggedIn == true,
                            onLogout = { authRepository.logout() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AuthLoadingGate(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavScaffold(
    navController: NavHostController,
    startDestination: String,
    showBottomBar: Boolean,
    showFab: Boolean,
    onLogout: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedItemIndex = BottomBarNavigationItems.items.indexOfFirst { item ->
        when (item.destination) {
            BottomBarDestination.Home -> currentRoute == Screen.Home.route
            BottomBarDestination.Habit -> currentRoute == Screen.Habit.route
            BottomBarDestination.Profile -> currentRoute == Screen.Profile.route
        }
    }.coerceAtLeast(0)

    val snackbarAddedMessage = stringResource(R.string.snackbar_habit_added)
    val snackbarDeletedMessage = stringResource(R.string.snackbar_habit_deleted)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                    items = BottomBarNavigationItems.items,
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { index ->
                        when (BottomBarNavigationItems.items[index].destination) {
                            BottomBarDestination.Home -> {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            BottomBarDestination.Habit -> {
                                navController.navigate(Screen.Habit.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            BottomBarDestination.Profile -> {
                                navController.navigate(Screen.Profile.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding: PaddingValues ->
        NavGraph(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            snackbarHostState = snackbarHostState,
            snackbarAddedMessage = snackbarAddedMessage,
            snackbarDeletedMessage = snackbarDeletedMessage,
            onLogout = onLogout
        )
    }
}
