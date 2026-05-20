package com.example.mobileprogrammingarchitecture.data.model.di

import android.content.Context
import androidx.room.Room
import com.example.mobileprogrammingarchitecture.data.model.local.DatabaseSeeder
import com.example.mobileprogrammingarchitecture.data.model.local.dao.AppSettingsDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.CategoryDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.CompletionLogDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.HabitCategoryCrossRefDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.HabitDao
import com.example.mobileprogrammingarchitecture.data.model.local.db.HabitTrackerDatabase
import com.example.mobileprogrammingarchitecture.data.repository.habit.HabitRepository
import com.example.mobileprogrammingarchitecture.data.repository.habit.impl.HabitRepositoryImpl
import com.example.mobileprogrammingarchitecture.data.repository.preferences.UserPreferencesRepository
import com.example.mobileprogrammingarchitecture.data.repository.preferences.impl.UserPreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HabitTrackerModule {

    private const val DB_NAME = "habit_tracker.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HabitTrackerDatabase =
        Room.databaseBuilder(context, HabitTrackerDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideHabitDao(database: HabitTrackerDatabase): HabitDao = database.habitDao()

    @Provides
    @Singleton
    fun provideCategoryDao(database: HabitTrackerDatabase): CategoryDao = database.categoryDao()

    @Provides
    @Singleton
    fun provideHabitCategoryCrossRefDao(database: HabitTrackerDatabase): HabitCategoryCrossRefDao =
        database.habitCategoryCrossRefDao()

    @Provides
    @Singleton
    fun provideCompletionLogDao(database: HabitTrackerDatabase): CompletionLogDao =
        database.completionLogDao()

    @Provides
    @Singleton
    fun provideAppSettingsDao(database: HabitTrackerDatabase): AppSettingsDao = database.appSettingsDao()

    @Provides
    @Singleton
    fun provideHabitRepository(
        database: HabitTrackerDatabase,
        habitDao: HabitDao,
        habitCategoryCrossRefDao: HabitCategoryCrossRefDao,
        completionLogDao: CompletionLogDao
    ): HabitRepository = HabitRepositoryImpl(
        database = database,
        habitDao = habitDao,
        habitCategoryCrossRefDao = habitCategoryCrossRefDao,
        completionLogDao = completionLogDao
    )

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(appSettingsDao: AppSettingsDao): UserPreferencesRepository =
        UserPreferencesRepositoryImpl(appSettingsDao)

    @Provides
    @Singleton
    fun provideDatabaseSeeder(
        database: HabitTrackerDatabase,
        habitDao: HabitDao,
        categoryDao: CategoryDao,
        habitCategoryCrossRefDao: HabitCategoryCrossRefDao,
        completionLogDao: CompletionLogDao,
        appSettingsDao: AppSettingsDao
    ): DatabaseSeeder = DatabaseSeeder(
        database = database,
        habitDao = habitDao,
        categoryDao = categoryDao,
        habitCategoryCrossRefDao = habitCategoryCrossRefDao,
        completionLogDao = completionLogDao,
        appSettingsDao = appSettingsDao
    )
}
