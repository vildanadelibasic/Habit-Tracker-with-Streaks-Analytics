package com.example.mobileprogrammingarchitecture.model.di

import android.content.Context
import androidx.room.Room
import com.example.mobileprogrammingarchitecture.model.util.DatabaseSeeder
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.AppSettingsDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.CategoryDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.CompletionLogDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitCategoryCrossRefDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.db.AppDatabase
import com.example.mobileprogrammingarchitecture.model.repository.habit.HabitRepositoryImpl
import com.example.mobileprogrammingarchitecture.model.repository.user.UserPreferencesRepositoryImpl
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRepository
import com.example.mobileprogrammingarchitecture.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "habit_tracker.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideHabitDao(database: AppDatabase): HabitDao = database.habitDao()

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()

    @Provides
    @Singleton
    fun provideHabitCategoryCrossRefDao(database: AppDatabase): HabitCategoryCrossRefDao =
        database.habitCategoryCrossRefDao()

    @Provides
    @Singleton
    fun provideCompletionLogDao(database: AppDatabase): CompletionLogDao =
        database.completionLogDao()

    @Provides
    @Singleton
    fun provideAppSettingsDao(database: AppDatabase): AppSettingsDao = database.appSettingsDao()

    @Provides
    @Singleton
    fun provideHabitRepository(
        database: AppDatabase,
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
        database: AppDatabase,
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
