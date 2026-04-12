package com.example.mobileprogrammingarchitecture.presentation.ui.screen.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailsScreen(
    habitName: String,
    modifier: Modifier = Modifier
) {
    Text(text = "Selected habit: $habitName")
}