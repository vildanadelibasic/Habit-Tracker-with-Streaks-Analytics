package com.example.mobileprogrammingarchitecture.presentation.util

import android.util.Patterns
import androidx.annotation.StringRes
import com.example.mobileprogrammingarchitecture.R

object Validation {

    @StringRes
    fun validateHabitName(name: String): Int? {
        if (name.isBlank()) return R.string.validation_habit_name_required
        if (name.trim().length < 3) return R.string.validation_habit_name_short
        return null
    }

    @StringRes
    fun validateFrequency(frequencyInput: String): Int? {
        if (frequencyInput.isBlank()) return R.string.validation_frequency_required
        val parsed = frequencyInput.toIntOrNull() ?: return R.string.validation_frequency_not_number
        if (parsed !in 1..7) return R.string.validation_frequency_range
        return null
    }

    @StringRes
    fun validateEmail(email: String): Int? {
        if (email.isBlank()) return R.string.validation_email_required
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return R.string.validation_email_invalid
        }
        return null
    }

    /** Login: password must not be empty. */
    @StringRes
    fun validatePasswordRequired(password: String): Int? {
        if (password.isBlank()) return R.string.validation_password_required
        return null
    }

    /** Registration: minimum length. */
    @StringRes
    fun validatePasswordStrength(password: String): Int? {
        if (password.isBlank()) return R.string.validation_password_required
        if (password.length < 6) return R.string.validation_password_short
        return null
    }

    @StringRes
    fun validateFullName(name: String): Int? {
        if (name.isBlank()) return R.string.validation_name_required
        if (name.trim().length < 2) return R.string.validation_name_short
        return null
    }

    @StringRes
    fun validatePasswordMatch(password: String, confirm: String): Int? {
        if (password != confirm) return R.string.validation_password_mismatch
        return null
    }
}
