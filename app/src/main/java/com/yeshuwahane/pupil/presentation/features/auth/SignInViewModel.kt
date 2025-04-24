package com.yeshuwahane.pupil.presentation.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.pupil.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState

    fun updateEmail(email: String) {
        val trimmedEmail = email.trim()
        val isValid = isValidEmail(trimmedEmail)
        _uiState.value = _uiState.value.copy(
            email = trimmedEmail,
            emailError = if (trimmedEmail.isNotBlank() && !isValid) "Invalid email format" else null
        )
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun signIn(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = repository.signIn(
                _uiState.value.email.trim(),
                _uiState.value.password.trim()
            )
            if (result) {
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Incorrect email or password."
                )
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
}
