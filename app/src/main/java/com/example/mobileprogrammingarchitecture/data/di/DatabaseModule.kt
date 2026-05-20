package com.example.mobileprogrammingarchitecture.data.di

import android.content.Context
import androidx.room.Room
import com.example.mobileprogrammingarchitecture.data.model.local.dao.AppSettingsDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.CategoryDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.CompletionLogDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.HabitCategoryCrossRefDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.HabitDao
import com.example.mobileprogrammingarchitecture.data.model.local.db.HabitTrackerDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): HabitTrackerDatabase =
        Room.databaseBuilder(context, HabitTrackerDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideHabitDao(database: HabitTrackerDatabase): HabitDao = database.habitDao()

    @Provides
    fun provideCategoryDao(database: HabitTrackerDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideHabitCategoryCrossRefDao(database: HabitTrackerDatabase): HabitCategoryCrossRefDao =
        database.habitCategoryCrossRefDao()

    @Provides
    fun provideCompletionLogDao(database: HabitTrackerDatabase): CompletionLogDao =
        database.completionLogDao()

    @Provides
    fun provideAppSettingsDao(database: HabitTrackerDatabase): AppSettingsDao = database.appSettingsDao()
}
