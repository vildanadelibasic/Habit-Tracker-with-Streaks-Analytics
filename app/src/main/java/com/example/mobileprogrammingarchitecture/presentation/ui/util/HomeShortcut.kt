package com.example.mobileprogrammingarchitecture.presentation.ui.util

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
