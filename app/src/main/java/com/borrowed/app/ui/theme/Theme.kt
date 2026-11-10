package com.borrowed.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorMutedDark = Color(0xFFB5C8C1)

private val BorrowedLightColourScheme = lightColorScheme(
    primary = BorrowedGreen,
    onPrimary = BorrowedWhite,
    primaryContainer = BorrowedMint,
    onPrimaryContainer = BorrowedGreenDark,
    secondary = BorrowedMuted,
    onSecondary = BorrowedWhite,
    background = BorrowedCream,
    onBackground = BorrowedInk,
    surface = BorrowedWhite,
    onSurface = BorrowedInk
)

private val BorrowedDarkColourScheme = darkColorScheme(
    primary = BorrowedMint,
    onPrimary = BorrowedGreenDark,
    primaryContainer = BorrowedGreenDark,
    onPrimaryContainer = BorrowedMint,
    secondary = ColorMutedDark,
    onSecondary = BorrowedDarkBackground,
    background = BorrowedDarkBackground,
    onBackground = BorrowedDarkText,
    surface = BorrowedDarkSurface,
    onSurface = BorrowedDarkText
)


@Composable
fun BorrowedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colourScheme = if (darkTheme) {
        BorrowedDarkColourScheme
    } else {
        BorrowedLightColourScheme
    }

    MaterialTheme(
        colorScheme = colourScheme,
        typography = BorrowedTypography,
        content = content
    )
}
