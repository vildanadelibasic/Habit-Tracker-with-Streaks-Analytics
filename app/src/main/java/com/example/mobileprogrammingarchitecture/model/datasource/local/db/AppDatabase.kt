package com.example.mobileprogrammingarchitecture.model.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.AppSettingsDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.CategoryDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.CompletionLogDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitCategoryCrossRefDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.dao.HabitDao
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.AppSettingEntity
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.CategoryEntity
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitCategoryCrossRef
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitCompletionLogEntity
import com.example.mobileprogrammingarchitecture.model.datasource.local.entity.HabitEntity

@Database(
    entities = [
        HabitEntity::class,
        CategoryEntity::class,
        HabitCategoryCrossRef::class,
        HabitCompletionLogEntity::class,
        AppSettingEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    abstract fun categoryDao(): CategoryDao

    abstract fun habitCategoryCrossRefDao(): HabitCategoryCrossRefDao

    abstract fun completionLogDao(): CompletionLogDao

    abstract fun appSettingsDao(): AppSettingsDao
}
