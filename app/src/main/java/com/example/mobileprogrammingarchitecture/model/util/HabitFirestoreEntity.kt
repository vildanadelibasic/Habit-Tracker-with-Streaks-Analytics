package com.example.mobileprogrammingarchitecture.model.util

data class HabitFirestoreEntity(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val frequency: String = "daily",
    val completed: Boolean = false
)
