package com.example.mobileprogrammingarchitecture.presentation.util

import android.util.Patterns

object Validation {

    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email is required."
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Enter a valid email address."
        return null
    }

    fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Password is required."
        if (password.length < 6) return "Password must be at least 6 characters."
        return null
    }

    fun validateName(name: String): String? {
        if (name.isBlank()) return "Full name is required."
        if (name.length < 2) return "Full name must be at least 2 characters."
        return null
    }

    fun validatePasswordMatch(password: String, confirm: String): String? {
        if (password != confirm) return "Passwords do not match."
        return null
    }
}
