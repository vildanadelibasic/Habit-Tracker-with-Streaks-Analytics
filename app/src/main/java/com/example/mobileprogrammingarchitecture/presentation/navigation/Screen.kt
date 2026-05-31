package com.example.mobileprogrammingarchitecture.presentation.navigation

sealed class Screen(val route: String) {
    data object Register : Screen("register_screen")
    data object Login : Screen("login_screen")
    data object Home : Screen("home_screen")
    data object Habit : Screen("habit_screen")
    data object AddHabit : Screen("add_habit_screen")
    data object Profile : Screen("profile_screen")
    data object About : Screen("about_screen")
    data object Settings : Screen("settings_screen")

    data class EditHabit(val habitId: Int) : Screen("edit_habit_screen/{habitId}") {
        companion object {
            fun createRoute(habitId: Int): String = "edit_habit_screen/$habitId"
        }
    }

    companion object {
        fun isBottomBarRoute(route: String?): Boolean {
            return route == Home.route ||
                route == Habit.route ||
                route == Profile.route
        }
    }
}
