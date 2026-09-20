package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val VaaniMitraLightColorScheme = lightColorScheme(
    primary = BrandBluePrimary,
    onPrimary = AppWhite,
    primaryContainer = BrandBlueLight,
    onPrimaryContainer = BrandBlueDark,
    secondary = BrandCyan,
    onSecondary = AppWhite,
    secondaryContainer = BrandCyanLight,
    onSecondaryContainer = BrandCyan,
    tertiary = BrandCyanAccent,
    onTertiary = AppWhite,
    background = AppWhite,
    onBackground = TextDarkNavy,
    surface = AppWhite,
    onSurface = TextDarkNavy,
    surfaceVariant = AppSurfaceLight,
    onSurfaceVariant = TextMutedSlate,
    outline = AppBorderLight,
    outlineVariant = AppBorderSubtle,
    error = ErrorRed,
    onError = AppWhite,
    errorContainer = ErrorRedLight,
    onErrorContainer = ErrorRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // We enforce the requested clean white-first modern UI
    MaterialTheme(
        colorScheme = VaaniMitraLightColorScheme,
        typography = Typography,
        content = content
    )
}

