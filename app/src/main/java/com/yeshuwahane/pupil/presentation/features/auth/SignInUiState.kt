package com.yeshuwahane.pupil.presentation.features.auth





data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
