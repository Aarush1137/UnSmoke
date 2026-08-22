package com.unsmoke.app.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightSurface,
    primaryContainer = LightPrimaryLight,
    onPrimaryContainer = LightPrimaryDark,
    secondary = LightLavender,
    onSecondary = LightSurface,
    background = LightBackground,
    onBackground = LightDarkText,
    surface = LightSurface,
    onSurface = LightDarkText,
    surfaceVariant = LightSoftSurface,
    onSurfaceVariant = LightSecondaryText,
    outline = LightBorder,
    outlineVariant = LightMuted,
    error = LightError,
    onError = LightSurface,
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkBackground,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkPrimaryText,
    surface = DarkSurface,
    onSurface = DarkPrimaryText,
    surfaceVariant = DarkElevatedSurface,
    onSurfaceVariant = DarkSecondaryText,
    outline = DarkBorder,
    outlineVariant = DarkMutedText,
    error = DarkError,
    onError = DarkBackground,
)

data class UnSmokeColors(
    val mint: Color,
    val gold: Color,
    val success: Color,
    val elevatedSurface: Color,
    val softSurface: Color,
    val mutedText: Color,
    val isDark: Boolean
)

val LocalUnSmokeColors = staticCompositionLocalOf {
    UnSmokeColors(
        mint = LightMint,
        gold = LightGold,
        success = LightSuccess,
        elevatedSurface = LightSurface,
        softSurface = LightSoftSurface,
        mutedText = LightMuted,
        isDark = false
    )
}

@Composable
fun UnSmokeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val unSmokeColors = if (darkTheme) {
        UnSmokeColors(
            mint = DarkPrimary,
            gold = DarkGold,
            success = DarkSuccess,
            elevatedSurface = DarkElevatedSurface,
            softSurface = DarkElevatedSurface,
            mutedText = DarkMutedText,
            isDark = true
        )
    } else {
        UnSmokeColors(
            mint = LightMint,
            gold = LightGold,
            success = LightSuccess,
            elevatedSurface = LightSurface,
            softSurface = LightSoftSurface,
            mutedText = LightMuted,
            isDark = false
        )
    }

    CompositionLocalProvider(LocalUnSmokeColors provides unSmokeColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = UnSmokeTypography,
            shapes = UnSmokeShapes,
            content = content
        )
    }
}

val MaterialTheme.unSmokeColors: UnSmokeColors
    @Composable get() = LocalUnSmokeColors.current
