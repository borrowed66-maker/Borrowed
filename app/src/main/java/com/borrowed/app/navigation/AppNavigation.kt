package com.borrowed.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.borrowed.app.R
import com.borrowed.app.ui.auth.AuthenticationScreen
import com.borrowed.app.ui.auth.SessionLoadingScreen
import com.borrowed.app.ui.item.AddEditItemScreen
import com.borrowed.app.ui.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

    if (authState.isCheckingSession) {
        SessionLoadingScreen()
        return
    }

    val startDestination = if (authState.isAuthenticated) {
        AppRoutes.HOME
    } else {
        AppRoutes.AUTHENTICATION
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppRoutes.AUTHENTICATION) {
            AuthenticationScreen(
                state = authState,
                onEmailChange = authViewModel::onEmailChange,
                onPasswordChange = authViewModel::onPasswordChange,
                onConfirmPasswordChange = authViewModel::onConfirmPasswordChange,
                onPasswordVisibilityChange = authViewModel::onPasswordVisibilityChange,
                onConfirmPasswordVisibilityChange = authViewModel::onConfirmPasswordVisibilityChange,
                onModeChange = authViewModel::onModeChange,
                onSubmit = {
                    if (authState.isSignUp) authViewModel.signUp() else authViewModel.signIn()
                }
            )
        }

        composable(AppRoutes.HOME) {
            HomePlaceholder(
                onAddItem = { navController.navigate(AppRoutes.ADD_EDIT_ITEM) },
                onSettings = { navController.navigate(AppRoutes.SETTINGS) },
                onSignOut = authViewModel::signOut
            )
        }

        composable(AppRoutes.ADD_EDIT_ITEM) {
            AddEditItemScreen(
                onSave = { /* Supabase saving is connected in Commit 10. */ },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.SETTINGS) {
            DestinationPlaceholder(
                title = stringResource(R.string.settings_destination),
                onBack = { navController.popBackStack() }
            )
        }
    }

    // Keep the navigation stack in step with the current Supabase session.
    LaunchedEffect(authState.isAuthenticated) {
        val destination = if (authState.isAuthenticated) {
            AppRoutes.HOME
        } else {
            AppRoutes.AUTHENTICATION
        }

        if (navController.currentDestination?.route != destination) {
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}

@Composable
private fun HomePlaceholder(
    onAddItem: () -> Unit,
    onSettings: () -> Unit,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.welcome_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.welcome_message),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onAddItem) {
            Text(text = stringResource(R.string.add_edit_destination))
        }
        Button(onClick = onSettings) {
            Text(text = stringResource(R.string.settings_destination))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSignOut) {
            Text(text = "Sign out")
        }
    }
}

@Composable
private fun DestinationPlaceholder(
    title: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.navigation_placeholder_message),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text(text = stringResource(R.string.back_button))
        }
    }
}
