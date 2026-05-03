package com.example.mobileprogrammingarchitecture.presentation.di

import androidx.compose.runtime.staticCompositionLocalOf

val LocalHabitTrackerViewModelFactory = staticCompositionLocalOf<HabitTrackerViewModelFactory> {
    error("HabitTrackerViewModelFactory is not provided")
}
