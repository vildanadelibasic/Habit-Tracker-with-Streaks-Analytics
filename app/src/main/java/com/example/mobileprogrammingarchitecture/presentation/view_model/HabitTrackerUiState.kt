package com.example.mobileprogrammingarchitecture.presentation.view_model

import androidx.annotation.StringRes
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen
import com.example.mobileprogrammingarchitecture.presentation.navigation.AuthScreen

data class HabitTrackerUiState(
    val isLoggedIn: Boolean = false,
    val authScreen: AuthScreen = AuthScreen.LOGIN,
    val loginEmail: String = "",
    val loginPassword: String = "",
    @StringRes val loginErrorRes: Int? = null,
    val regFullName: String = "",
    val regEmail: String = "",
    val regPassword: String = "",
    val regConfirmPassword: String = "",
    @StringRes val registrationErrorRes: Int? = null,
    val selectedScreen: AppScreen = AppScreen.HOME,
    val habits: List<Habit> = emptyList(),
    val nameInput: String = "",
    val frequencyInput: String = "",
    val remindersEnabled: Boolean = true,
    @StringRes val formErrorRes: Int? = null
)
