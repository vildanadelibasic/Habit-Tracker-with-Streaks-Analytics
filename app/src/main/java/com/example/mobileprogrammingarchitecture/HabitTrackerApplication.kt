package com.example.mobileprogrammingarchitecture

import android.app.Application
import com.example.mobileprogrammingarchitecture.data.repository.DefaultHabitRepository
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.data.repository.PreferencesRepository
import com.example.mobileprogrammingarchitecture.presentation.di.HabitTrackerViewModelFactory

class HabitTrackerApplication : Application() {

    lateinit var habitRepository: HabitRepository
        private set

    lateinit var preferencesRepository: PreferencesRepository
        private set

    lateinit var viewModelFactory: HabitTrackerViewModelFactory
        private set

    override fun onCreate() {
        super.onCreate()
        habitRepository = DefaultHabitRepository()
        preferencesRepository = PreferencesRepository()
        viewModelFactory = HabitTrackerViewModelFactory(habitRepository, preferencesRepository)
    }
}
