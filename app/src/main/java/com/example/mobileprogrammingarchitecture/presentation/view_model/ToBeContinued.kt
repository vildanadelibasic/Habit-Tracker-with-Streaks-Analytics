package com.example.mobileprogrammingarchitecture.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.mobileprogrammingarchitecture.data.util.Habit
import com.example.mobileprogrammingarchitecture.presentation.navigation.AppScreen
import com.example.mobileprogrammingarchitecture.presentation.navigation.AuthScreen
import com.example.mobileprogrammingarchitecture.presentation.util.Validation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HabitTrackerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HabitTrackerUiState(
            habits = listOf(
                Habit(id = 1, name = "10k koraka dnevno", frequencyPerWeek = 7, currentStreak = 4, reminderEnabled = true),
                Habit(id = 2, name = "Učenje", frequencyPerWeek = 5, currentStreak = 3, reminderEnabled = true),
                Habit(id = 3, name = "Coding", frequencyPerWeek = 6, currentStreak = 5, reminderEnabled = true),
                Habit(id = 4, name = "Druženje", frequencyPerWeek = 3, currentStreak = 2, reminderEnabled = false),
                Habit(id = 5, name = "Spavanje", frequencyPerWeek = 7, currentStreak = 10, reminderEnabled = true),
                Habit(id = 6, name = "2 L vode dnevno", frequencyPerWeek = 7, currentStreak = 7, reminderEnabled = true),
                Habit(id = 7, name = "3 obroka + užine", frequencyPerWeek = 7, currentStreak = 4, reminderEnabled = false)
            )
        )
    )
    val uiState: StateFlow<HabitTrackerUiState> = _uiState

    fun selectScreen(screen: AppScreen) {
        _uiState.update { it.copy(selectedScreen = screen) }
    }

    fun onLoginEmailChange(value: String) {
        _uiState.update { it.copy(loginEmail = value, loginErrorRes = null) }
    }

    fun onLoginPasswordChange(value: String) {
        _uiState.update { it.copy(loginPassword = value, loginErrorRes = null) }
    }

    fun onRegFullNameChange(value: String) {
        _uiState.update { it.copy(regFullName = value, registrationErrorRes = null) }
    }

    fun onRegEmailChange(value: String) {
        _uiState.update { it.copy(regEmail = value, registrationErrorRes = null) }
    }

    fun onRegPasswordChange(value: String) {
        _uiState.update { it.copy(regPassword = value, registrationErrorRes = null) }
    }

    fun onRegConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(regConfirmPassword = value, registrationErrorRes = null) }
    }

    fun goToRegistration() {
        _uiState.update {
            it.copy(
                authScreen = AuthScreen.REGISTRATION,
                loginErrorRes = null,
                registrationErrorRes = null
            )
        }
    }

    fun goToLogin() {
        _uiState.update {
            it.copy(
                authScreen = AuthScreen.LOGIN,
                loginErrorRes = null,
                registrationErrorRes = null
            )
        }
    }

    fun canSubmitLogin(): Boolean {
        val s = _uiState.value
        return Validation.validateEmail(s.loginEmail) == null &&
            Validation.validatePasswordRequired(s.loginPassword) == null
    }

    fun submitLogin() {
        val s = _uiState.value
        val emailErr = Validation.validateEmail(s.loginEmail)
        val passErr = Validation.validatePasswordRequired(s.loginPassword)
        val first = emailErr ?: passErr
        if (first != null) {
            _uiState.update { it.copy(loginErrorRes = first) }
            return
        }
        _uiState.update {
            it.copy(
                isLoggedIn = true,
                loginErrorRes = null,
                selectedScreen = AppScreen.HOME
            )
        }
    }

    fun canSubmitRegistration(): Boolean {
        val s = _uiState.value
        return Validation.validateFullName(s.regFullName) == null &&
            Validation.validateEmail(s.regEmail) == null &&
            Validation.validatePasswordStrength(s.regPassword) == null &&
            Validation.validatePasswordMatch(s.regPassword, s.regConfirmPassword) == null
    }

    fun submitRegistration() {
        val s = _uiState.value
        val nameErr = Validation.validateFullName(s.regFullName)
        val emailErr = Validation.validateEmail(s.regEmail)
        val passErr = Validation.validatePasswordStrength(s.regPassword)
        val matchErr = Validation.validatePasswordMatch(s.regPassword, s.regConfirmPassword)
        val first = nameErr ?: emailErr ?: passErr ?: matchErr
        if (first != null) {
            _uiState.update { it.copy(registrationErrorRes = first) }
            return
        }
        _uiState.update {
            it.copy(
                isLoggedIn = true,
                registrationErrorRes = null,
                authScreen = AuthScreen.LOGIN,
                selectedScreen = AppScreen.HOME,
                regFullName = "",
                regEmail = "",
                regPassword = "",
                regConfirmPassword = ""
            )
        }
    }

    fun onNameInputChange(value: String) {
        _uiState.update { it.copy(nameInput = value, formErrorRes = null) }
    }

    fun onFrequencyInputChange(value: String) {
        _uiState.update { it.copy(frequencyInput = value, formErrorRes = null) }
    }

    fun onReminderToggle(enabled: Boolean) {
        _uiState.update { it.copy(remindersEnabled = enabled) }
    }

    fun canSubmitForm(): Boolean {
        val state = _uiState.value
        return Validation.validateHabitName(state.nameInput) == null &&
            Validation.validateFrequency(state.frequencyInput) == null
    }

    fun submitHabit() {
        val state = _uiState.value
        val nameError = Validation.validateHabitName(state.nameInput)
        val frequencyError = Validation.validateFrequency(state.frequencyInput)
        val firstError = nameError ?: frequencyError

        if (firstError != null) {
            _uiState.update { it.copy(formErrorRes = firstError) }
            return
        }

        val nextId = (state.habits.maxOfOrNull { it.id } ?: 0) + 1
        val newHabit = Habit(
            id = nextId,
            name = state.nameInput.trim(),
            frequencyPerWeek = state.frequencyInput.toInt(),
            currentStreak = 0,
            reminderEnabled = state.remindersEnabled
        )

        _uiState.update {
            it.copy(
                habits = it.habits + newHabit,
                nameInput = "",
                frequencyInput = "",
                remindersEnabled = true,
                formErrorRes = null,
                selectedScreen = AppScreen.HABITS
            )
        }
    }

    fun clearAllHabits() {
        _uiState.update { it.copy(habits = emptyList()) }
    }
}
