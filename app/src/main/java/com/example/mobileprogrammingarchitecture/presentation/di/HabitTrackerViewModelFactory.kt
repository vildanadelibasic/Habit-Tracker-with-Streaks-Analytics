package com.example.mobileprogrammingarchitecture.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.data.repository.PreferencesRepository
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.AboutViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.AddHabitViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HabitsViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.HomeViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.ProfileViewModel
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.SettingsViewModel

class HabitTrackerViewModelFactory(
    val habitRepository: HabitRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(habitRepository) as T
            modelClass.isAssignableFrom(HabitsViewModel::class.java) ->
                HabitsViewModel(habitRepository) as T
            modelClass.isAssignableFrom(AddHabitViewModel::class.java) ->
                AddHabitViewModel(habitRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(habitRepository) as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) ->
                AboutViewModel(habitRepository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(preferencesRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
