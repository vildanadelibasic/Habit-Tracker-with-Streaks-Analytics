package com.example.mobileprogrammingarchitecture.data.di

import com.example.mobileprogrammingarchitecture.data.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.data.repository.HabitRepositoryImpl
import com.example.mobileprogrammingarchitecture.data.repository.UserPreferencesRepository
import com.example.mobileprogrammingarchitecture.data.repository.UserPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHabitRepository(impl: HabitRepositoryImpl): HabitRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(impl: UserPreferencesRepositoryImpl): UserPreferencesRepository
}
