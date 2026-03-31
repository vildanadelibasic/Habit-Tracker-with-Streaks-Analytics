package com.example.mobileprogrammingarchitecture.presentation.ui.screen.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.presentation.theme.AppShapes
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme

@Composable
fun ProfileInfoSection(
    fullName: String,
    email: String,
    role: String,
    modifier: Modifier = Modifier
) {
    val cardPadding = dimensionResource(R.dimen.padding_card)
    val sectionSmall = dimensionResource(R.dimen.spacing_section_small)

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.card,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(cardPadding)) {
            Text(
                text = stringResource(R.string.profile_info_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(sectionSmall))
            Text(
                text = stringResource(R.string.profile_name, fullName),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.profile_email, email),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.profile_role, role),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Profile card", showBackground = true)
@Composable
private fun ProfileInfoSectionCardPreview() {
    HabitTrackerPreviewTheme {
        ProfileInfoSection(
            fullName = stringResource(R.string.profile_demo_name),
            email = stringResource(R.string.profile_demo_email),
            role = stringResource(R.string.profile_demo_role)
        )
    }
}
