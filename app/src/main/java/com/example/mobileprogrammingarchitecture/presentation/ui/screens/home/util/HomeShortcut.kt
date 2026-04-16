package com.example.mobileprogrammingarchitecture.presentation.ui.screens.home.util

data class HomeShortcut(
    val id: Int,
    val title: String,
    val target: ShortcutTarget
)

enum class ShortcutTarget {
    Habits,
    Profile,
    About,
    Settings
}
