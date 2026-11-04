package com.example.borrowed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.borrowed.data.repository.AuthRepository
import com.example.borrowed.ui.navigation.AppNavigation
import com.example.borrowed.ui.theme.BorrowedTheme

class MainActivity : ComponentActivity() {

    private val authRepository: AuthRepository by lazy {
        (application as BorrowedApp).authRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = if (authRepository.isLoggedIn) {
            "home"
        } else {
            "splash"
        }

        setContent {
            BorrowedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}