package com.borrowed.app.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.borrowed.app.ui.viewmodel.AuthUiState

@Composable
fun AuthenticationScreen(
    state: AuthUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onConfirmPasswordVisibilityChange: () -> Unit,
    onModeChange: () -> Unit,
    onSubmit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Borrowed",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (state.isSignUp) "Create your account" else "Welcome back",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Keep track of the things you lend.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(28.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email address") },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    singleLine = true,
                    enabled = !state.isLoading,
                    visualTransformation = if (state.isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        TextButton(onClick = onPasswordVisibilityChange) {
                            Text(if (state.isPasswordVisible) "Hide" else "Show")
                        }
                    }
                )

                if (state.isSignUp) {
                    Spacer(Modifier.height(14.dp))
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Confirm password") },
                        singleLine = true,
                        enabled = !state.isLoading,
                        visualTransformation = if (state.isConfirmPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            TextButton(onClick = onConfirmPasswordVisibilityChange) {
                                Text(if (state.isConfirmPasswordVisible) "Hide" else "Show")
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Use at least 6 characters for your password.",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                state.errorMessage?.let { message ->
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = message,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                state.infoMessage?.let { message ->
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = message,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(if (state.isSignUp) "Create account" else "Sign in")
                    }
                }

                Spacer(Modifier.height(12.dp))
                TextButton(
                    onClick = onModeChange,
                    enabled = !state.isLoading
                ) {
                    Text(
                        if (state.isSignUp) {
                            "Already have an account? Sign in"
                        } else {
                            "New to Borrowed? Create an account"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SessionLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text("Checking your session…")
        }
    }
}
