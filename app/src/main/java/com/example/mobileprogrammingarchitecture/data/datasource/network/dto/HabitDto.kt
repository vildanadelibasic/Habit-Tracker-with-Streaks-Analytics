package com.example.mobileprogrammingarchitecture.data.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class HabitDto(
    val id: Int,
    val title: String,
    val description: String,
    val frequency: String,
    val completed: Boolean,
    @SerializedName("user_id")
    val userId: Int
)

data class CreateHabitDto(
    val title: String,
    val description: String,
    val frequency: String,
    val completed: Boolean = false,
    @SerializedName("user_id")
    val userId: Int = 1
)

data class UpdateHabitDto(
    val title: String? = null,
    val description: String? = null,
    val frequency: String? = null,
    val completed: Boolean? = null,
    @SerializedName("user_id")
    val userId: Int? = null
)
