package com.example.borrowed.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = PrimaryLight.copy(alpha = 0.2f),
    onPrimaryContainer = PrimaryDark,
    secondary = Secondary,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = Secondary.copy(alpha = 0.2f),
    onSecondaryContainer = SecondaryDark,
    tertiary = Accent,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    background = BackgroundLight,
    onBackground = androidx.compose.ui.graphics.Color(0xFF1C1B1F),
    surface = SurfaceLight,
    onSurface = androidx.compose.ui.graphics.Color(0xFF1C1B1F),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE7E0EC),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF49454F),
    error = StatusOverdue,
    onError = androidx.compose.ui.graphics.Color.White,
    errorContainer = StatusOverdue.copy(alpha = 0.1f),
    onErrorContainer = StatusOverdue,
    outline = androidx.compose.ui.graphics.Color(0xFF79747E),
    outlineVariant = androidx.compose.ui.graphics.Color(0xFFCAC4D0),
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = PrimaryDark,
    primaryContainer = PrimaryDark.copy(alpha = 0.3f),
    onPrimaryContainer = PrimaryLight,
    secondary = Secondary,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = SecondaryDark.copy(alpha = 0.3f),
    onSecondaryContainer = Secondary,
    tertiary = Accent,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    background = androidx.compose.ui.graphics.Color(0xFF1C1B1F),
    onBackground = androidx.compose.ui.graphics.Color(0xFFE6E1E5),
    surface = androidx.compose.ui.graphics.Color(0xFF2B2930),
    onSurface = androidx.compose.ui.graphics.Color(0xFFE6E1E5),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF49454F),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFCAC4D0),
    error = StatusOverdue,
    onError = androidx.compose.ui.graphics.Color.White,
    errorContainer = StatusOverdue.copy(alpha = 0.2f),
    onErrorContainer = StatusOverdue.copy(alpha = 0.8f),
    outline = androidx.compose.ui.graphics.Color(0xFF938F99),
    outlineVariant = androidx.compose.ui.graphics.Color(0xFF49454F),
)

@Composable
fun BorrowedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}