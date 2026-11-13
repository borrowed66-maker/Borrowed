package com.borrowed.app.ui.viewmodel

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isSignUp: Boolean = false,
    val isLoading: Boolean = false,
    val isCheckingSession: Boolean = true,
    val isAuthenticated: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val errorMessage: String? = null,
    val infoMessage: String? = null
)
