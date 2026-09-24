package com.example.cst438_team1_project1

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppLightColors = lightColorScheme(
    primary = DarkRoyalBlue,
    onPrimary = White,

    secondary = SkyBlue,
    onSecondary = White,

    tertiary = NeonBlue,
    onTertiary = Navy,

    background = AppBackground,
    onBackground = PrimaryText,

    surface = White,
    onSurface = PrimaryText,

    error = ErrorRed
)

@Composable
fun CryptoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppLightColors,
        content = content
    )
}