package com.example.mobileprogrammingarchitecture.presentation.ui.screens.profile.components

data class ProfileRowData(
    val id: Int,
    val title: String,
    val subtitle: String,
    val onClick: (() -> Unit)? = null
)
