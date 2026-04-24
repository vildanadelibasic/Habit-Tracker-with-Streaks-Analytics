package com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.navigation.Screen

object BottomBarNavigationItems {
    val items: List<BottomBarNavigationItem> = listOf(
        BottomBarNavigationItem(
            titleId = R.string.nav_home,
            icon = Icons.Outlined.Home,
            route = Screen.Home.route
        ),
        BottomBarNavigationItem(
            titleId = R.string.nav_habits,
            icon = Icons.Outlined.List,
            route = Screen.Habit.route
        ),
        BottomBarNavigationItem(
            titleId = R.string.nav_profile,
            icon = Icons.Outlined.Person,
            route = Screen.Profile.route
        )
    )
}
