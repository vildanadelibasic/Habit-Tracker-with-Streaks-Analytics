package com.example.mobileprogrammingarchitecture.presentation.navigation.bottom_bar

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarNavigationItem(
    @StringRes val titleId: Int,
    val icon: ImageVector,
    val destination: BottomBarDestination
)
