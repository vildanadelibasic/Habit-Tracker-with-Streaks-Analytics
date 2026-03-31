package com.example.mobileprogrammingarchitecture.presentation.navigation

import androidx.annotation.StringRes
import com.example.mobileprogrammingarchitecture.R

enum class AppScreen(@StringRes val titleRes: Int) {
    HOME(R.string.nav_home),
    HABITS(R.string.nav_habits),
    ADD_HABIT(R.string.nav_add_habit),
    ANALYTICS(R.string.nav_analytics),
    PROFILE(R.string.nav_profile)
}
