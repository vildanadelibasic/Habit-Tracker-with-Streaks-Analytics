package com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import com.example.mobileprogrammingarchitecture.R

object BottomBarNavigationItems {
    val items: List<BottomBarNavigationItem> = listOf(
        BottomBarNavigationItem(
            titleId = R.string.nav_home,
            icon = Icons.Outlined.Home,
            destination = BottomBarDestination.Home
        ),
        BottomBarNavigationItem(
            titleId = R.string.nav_habits,
            icon = Icons.Outlined.List,
            destination = BottomBarDestination.Habit
        ),
        BottomBarNavigationItem(
            titleId = R.string.nav_profile,
            icon = Icons.Outlined.Person,
            destination = BottomBarDestination.Profile
        )
    )
}
