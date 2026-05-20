package com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate

sealed interface HabitDetailsEffect {
    data object Deleted : HabitDetailsEffect
}
