package com.borrowed.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.borrowed.app.navigation.AppNavigation
import com.borrowed.app.ui.theme.BorrowedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BorrowedTheme {
                BorrowedApp()
            }
        }
    }
}

@Composable
fun BorrowedApp() {
    AppNavigation()
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    BorrowedTheme {
        BorrowedApp()
    }
}
