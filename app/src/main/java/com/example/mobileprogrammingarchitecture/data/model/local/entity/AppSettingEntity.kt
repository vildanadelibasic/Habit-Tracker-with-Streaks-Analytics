package com.example.mobileprogrammingarchitecture.data.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettingEntity(
    @PrimaryKey val settingKey: String,
    val value: String
)
