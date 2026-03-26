package com.example.mobileprogrammingarchitecture.data.util

data class Habit(
    val id: Int,
    val name: String,
    val frequencyPerWeek: Int,
    val currentStreak: Int,
    val reminderEnabled: Boolean
)
