package com.example.mobileprogrammingarchitecture.presentation.view_model.habit.edit_habit

sealed interface EditHabitNavigationEvent {
    data object Deleted : EditHabitNavigationEvent
}
