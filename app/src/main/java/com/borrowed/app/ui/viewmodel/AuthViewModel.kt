package com.borrowed.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borrowed.app.data.repository.AuthRepository
import com.borrowed.app.data.remote.SupabaseClientProvider
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    // Initialise the repository only when it is first needed.
    private val repository by lazy { AuthRepository(SupabaseClientProvider.client) }

    init {
        observeSession()
    }

    private fun observeSession() {
        viewModelScope.launch {
            try {
                repository.sessionStatus.collect { status ->
                    when (status) {
                        SessionStatus.Initializing -> {
                            _uiState.update { it.copy(isCheckingSession = true) }
                        }

                        is SessionStatus.Authenticated -> {
                            _uiState.update {
                                it.copy(
                                    isCheckingSession = false,
                                    isAuthenticated = true,
                                    errorMessage = null
                                )
                            }
                        }

                        is SessionStatus.NotAuthenticated -> {
                            _uiState.update {
                                it.copy(
                                    isCheckingSession = false,
                                    isAuthenticated = false
                                )
                            }
                        }

                        is SessionStatus.RefreshFailure -> {
                            _uiState.update {
                                it.copy(
                                    isCheckingSession = false,
                                    isAuthenticated = false,
                                    errorMessage = "Your session could not be refreshed. Please sign in again."
                                )
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isCheckingSession = false,
                        isAuthenticated = false,
                        errorMessage = configurationOrNetworkMessage(exception)
                    )
                }
            }
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorMessage = null, infoMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null, infoMessage = null) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value, errorMessage = null) }
    }

    fun onModeChange() {
        _uiState.update {
            it.copy(
                isSignUp = !it.isSignUp,
                confirmPassword = "",
                errorMessage = null,
                infoMessage = null
            )
        }
    }

    fun onPasswordVisibilityChange() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onConfirmPasswordVisibilityChange() {
        _uiState.update {
            it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible)
        }
    }

    fun signIn() {
        val state = _uiState.value
        val validationMessage = validateEmailAndPassword(state.email, state.password)
        if (validationMessage != null) {
            _uiState.update { it.copy(errorMessage = validationMessage, infoMessage = null) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, infoMessage = null) }
            try {
                repository.signIn(state.email.trim(), state.password)
            } catch (exception: Exception) {
                _uiState.update { it.copy(errorMessage = readableError(exception)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun signUp() {
        val state = _uiState.value
        val validationMessage = validateEmailAndPassword(state.email, state.password)
        if (validationMessage != null) {
            _uiState.update { it.copy(errorMessage = validationMessage, infoMessage = null) }
            return
        }
        if (state.password.length < 6) {
            _uiState.update {
                it.copy(errorMessage = "Use a password with at least 6 characters.")
            }
            return
        }
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "The passwords do not match.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, infoMessage = null) }
            try {
                repository.signUp(state.email.trim(), state.password)
                _uiState.update {
                    it.copy(
                        infoMessage = "Account request submitted. If email confirmation is enabled, check your inbox before signing in."
                    )
                }
            } catch (exception: Exception) {
                _uiState.update { it.copy(errorMessage = readableError(exception)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, infoMessage = null) }
            try {
                repository.signOut()
            } catch (exception: Exception) {
                _uiState.update { it.copy(errorMessage = readableError(exception)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateEmailAndPassword(email: String, password: String): String? {
        if (email.isBlank()) return "Enter your email address."
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return "Enter a valid email address."
        }
        if (password.isBlank()) return "Enter your password."
        return null
    }

    private fun configurationOrNetworkMessage(exception: Exception): String {
        val message = exception.message.orEmpty()
        return if (message.contains("SUPABASE", ignoreCase = true)) {
            "Supabase configuration is missing. Add the project URL and publishable key to local.properties."
        } else {
            "Unable to initialise authentication. Check your internet connection and Supabase configuration."
        }
    }

    private fun readableError(exception: Exception): String {
        val message = exception.message?.trim().orEmpty()
        return if (message.isBlank()) {
            "Something went wrong. Please try again."
        } else {
            message.take(220)
        }
    }
}
