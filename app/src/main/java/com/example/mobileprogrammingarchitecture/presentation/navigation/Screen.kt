package com.example.mobileprogrammingarchitecture.presentation.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Login : Screen("login_screen")
    data object Register : Screen("register_screen")
    data object Home : Screen("home_screen")
    data object Habit : Screen("habit_screen")
    data object AddHabit : Screen("add_habit_screen")
    data object Profile : Screen("profile_screen")
    data object About : Screen("about_screen")
    data object Settings : Screen("settings_screen")

    data object HabitDetails : Screen("habit_details/{id}/{title}") {
        fun createRoute(id: Int, title: String): String {
            val encoded = Uri.encode(title)
            return "habit_details/$id/$encoded"
        }
    }

    companion object {
        fun bottomNavRoutes(): List<String> = listOf(
            Home.route,
            Habit.route,
            Profile.route
        )
    }
}
