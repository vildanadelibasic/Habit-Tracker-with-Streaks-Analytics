package com.example.mobileprogrammingarchitecture.presentation.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Habit : Screen("habit_screen")
    data object AddHabit : Screen("add_habit_screen")
    data object Profile : Screen("profile_screen")

    data object HabitDetails : Screen("habit_details/{id}/{title}") {
        fun createRoute(id: Int, title: String): String {
            val encoded = Uri.encode(title)
            return "habit_details/$id/$encoded"
        }
    }
}
