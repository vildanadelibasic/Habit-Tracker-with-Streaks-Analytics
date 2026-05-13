package com.example.mobileprogrammingarchitecture.data.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val categoryId: Int,
    val name: String
)
