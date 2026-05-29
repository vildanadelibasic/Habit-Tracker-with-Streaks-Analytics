package com.example.mobileprogrammingarchitecture.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobileprogrammingarchitecture.data.datasource.local.entity.AppSettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppSettingsDao {

    @Query("SELECT * FROM app_settings WHERE settingKey = :key LIMIT 1")
    fun observeSetting(key: String): Flow<AppSettingEntity?>

    @Query("SELECT * FROM app_settings WHERE settingKey = :key LIMIT 1")
    suspend fun getSetting(key: String): AppSettingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: AppSettingEntity)
}
