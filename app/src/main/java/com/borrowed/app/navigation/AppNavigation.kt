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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.borrowed.app.R

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME
    ) {
        composable(AppRoutes.HOME) {
            HomePlaceholder(
                onAddItem = { navController.navigate(AppRoutes.ADD_EDIT_ITEM) },
                onSettings = { navController.navigate(AppRoutes.SETTINGS) },
                onAuthentication = { navController.navigate(AppRoutes.AUTHENTICATION) }
            )
        }

        composable(AppRoutes.ADD_EDIT_ITEM) {
            DestinationPlaceholder(
                title = stringResource(R.string.add_edit_destination),
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.SETTINGS) {
            DestinationPlaceholder(
                title = stringResource(R.string.settings_destination),
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.AUTHENTICATION) {
            DestinationPlaceholder(
                title = stringResource(R.string.authentication_destination),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun HomePlaceholder(
    onAddItem: () -> Unit,
    onSettings: () -> Unit,
    onAuthentication: () -> Unit
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
        Button(onClick = onAuthentication) {
            Text(text = stringResource(R.string.authentication_destination))
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
