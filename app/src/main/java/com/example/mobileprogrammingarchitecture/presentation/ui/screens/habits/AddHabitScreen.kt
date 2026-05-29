package com.example.mobileprogrammingarchitecture.presentation.ui.screens.habits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobileprogrammingarchitecture.R
import com.example.mobileprogrammingarchitecture.domain.data.HabitDifficulty
import com.example.mobileprogrammingarchitecture.presentation.theme.HabitTrackerPreviewTheme
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.uistate.AddHabitUiState
import com.example.mobileprogrammingarchitecture.presentation.viewmodel.AddHabitViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun AddHabitScreen(
    viewModel: AddHabitViewModel,
    onSaveSuccess: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.saveCompleted.collect {
            onSaveSuccess()
        }
    }
    when (val s = uiState) {
        is AddHabitUiState.Success ->
            AddHabitScreenContent(
                title = s.title,
                onTitleChange = viewModel::setTitle,
                description = s.description,
                onDescriptionChange = viewModel::setDescription,
                difficulty = s.difficulty,
                onDifficultyChange = viewModel::setDifficulty,
                isDaily = s.isDaily,
                onIsDailyChange = viewModel::setIsDaily,
                isFormValid = s.isFormValid,
                isSaving = s.isSaving,
                onSaveClick = { viewModel.saveHabit() },
                onCancel = onCancel,
                modifier = modifier
            )
        AddHabitUiState.Init,
        AddHabitUiState.Loading ->
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        is AddHabitUiState.Error ->
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(s.message)
            }
    }
}

@Composable
private fun AddHabitScreenContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    difficulty: HabitDifficulty,
    onDifficultyChange: (HabitDifficulty) -> Unit,
    isDaily: Boolean,
    onIsDailyChange: (Boolean) -> Unit,
    isFormValid: Boolean,
    isSaving: Boolean,
    onSaveClick: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.add_habit_screen_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text(stringResource(R.string.label_title)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        item {
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text(stringResource(R.string.label_description)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
        }
        item {
            Text(stringResource(R.string.label_difficulty), style = MaterialTheme.typography.titleSmall)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    DifficultyOptionRow(
                        label = stringResource(R.string.difficulty_easy),
                        selected = difficulty == HabitDifficulty.Easy,
                        onSelect = { onDifficultyChange(HabitDifficulty.Easy) }
                    )
                }
                item {
                    DifficultyOptionRow(
                        label = stringResource(R.string.difficulty_medium),
                        selected = difficulty == HabitDifficulty.Medium,
                        onSelect = { onDifficultyChange(HabitDifficulty.Medium) }
                    )
                }
                item {
                    DifficultyOptionRow(
                        label = stringResource(R.string.difficulty_hard),
                        selected = difficulty == HabitDifficulty.Hard,
                        onSelect = { onDifficultyChange(HabitDifficulty.Hard) }
                    )
                }
            }
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = isDaily, onCheckedChange = onIsDailyChange)
                Text(
                    text = stringResource(R.string.daily_habit),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        item {
            if (title.isBlank()) {
                Text(
                    text = stringResource(R.string.validation_habit_name_required),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        item {
            Button(
                onClick = onSaveClick,
                enabled = isFormValid && !isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (isSaving) {
                        stringResource(R.string.add_habit_saving)
                    } else {
                        stringResource(R.string.action_save)
                    }
                )
            }
        }
        item {
            Button(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.action_cancel))
            }
        }
    }
}

@Composable
private fun DifficultyOptionRow(
    label: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onSelect,
                role = Role.RadioButton
            )
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(text = label, modifier = Modifier.padding(start = 4.dp))
    }
}

@Preview(showBackground = true, name = "Add habit")
@Composable
private fun AddHabitScreenPreview() {
    HabitTrackerPreviewTheme {
        AddHabitScreenContent(
            title = "",
            onTitleChange = {},
            description = "",
            onDescriptionChange = {},
            difficulty = HabitDifficulty.Medium,
            onDifficultyChange = {},
            isDaily = true,
            onIsDailyChange = {},
            isFormValid = false,
            isSaving = false,
            onSaveClick = {},
            onCancel = {}
        )
    }
}
