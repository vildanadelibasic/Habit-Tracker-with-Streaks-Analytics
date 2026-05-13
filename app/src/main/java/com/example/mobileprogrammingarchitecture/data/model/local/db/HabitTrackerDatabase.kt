package com.example.mobileprogrammingarchitecture.data.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobileprogrammingarchitecture.data.model.local.dao.AppSettingsDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.CategoryDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.CompletionLogDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.HabitCategoryCrossRefDao
import com.example.mobileprogrammingarchitecture.data.model.local.dao.HabitDao
import com.example.mobileprogrammingarchitecture.data.model.local.entity.AppSettingEntity
import com.example.mobileprogrammingarchitecture.data.model.local.entity.CategoryEntity
import com.example.mobileprogrammingarchitecture.data.model.local.entity.HabitCategoryCrossRef
import com.example.mobileprogrammingarchitecture.data.model.local.entity.HabitCompletionLogEntity
import com.example.mobileprogrammingarchitecture.data.model.local.entity.HabitEntity

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
abstract class HabitTrackerDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    abstract fun categoryDao(): CategoryDao

    abstract fun habitCategoryCrossRefDao(): HabitCategoryCrossRefDao

    abstract fun completionLogDao(): CompletionLogDao

    abstract fun appSettingsDao(): AppSettingsDao
}
