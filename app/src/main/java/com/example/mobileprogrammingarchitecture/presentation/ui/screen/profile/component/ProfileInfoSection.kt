package com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileprogrammingarchitecture.presentation.theme.MobileProgrammingArchitectureTheme

@Composable
fun ProfileInfoSection(
    fullName: String,
    email: String,
    role: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Profile Info",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name: $fullName")
            Text(text = "Email: $email")
            Text(text = "Role: $role")
        }
    }
}

@Preview
@Composable
private fun ProfileInfoSectionCardPreview() {
    MobileProgrammingArchitectureTheme {
        ProfileInfoSection(
            fullName = "Name",
            email = "ilma@gmail.com",
            role = "assistant"
        )
    }
}