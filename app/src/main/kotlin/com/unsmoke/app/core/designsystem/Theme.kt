package com.unsmoke.app.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
    scrim = LightScrim,
    inverseSurface = LightInverseSurface,
    inverseOnSurface = LightInverseOnSurface,
    inversePrimary = LightInversePrimary,
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
    scrim = DarkScrim,
    inverseSurface = DarkInverseSurface,
    inverseOnSurface = DarkInverseOnSurface,
    inversePrimary = DarkInversePrimary,
)

// Extended UnSmoke colors available via LocalUnSmokeColors
data class UnSmokeColors(
    val success: Color,
    val successContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val info: Color,
    val infoContainer: Color,
    val achievementAmber: Color,
    val achievementAmberContainer: Color,
    val orbCore: Color,
    val orbGlow: Color,
    val isDark: Boolean
)

val LocalUnSmokeColors = staticCompositionLocalOf {
    UnSmokeColors(
        success = SuccessLight,
        successContainer = SuccessContainerLight,
        warning = WarningLight,
        warningContainer = WarningContainerLight,
        info = InfoLight,
        infoContainer = InfoContainerLight,
        achievementAmber = AchievementAmber,
        achievementAmberContainer = AchievementAmberLight,
        orbCore = OrbCoreLight,
        orbGlow = OrbGlowLight,
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
            success = SuccessDark,
            successContainer = SuccessContainerDark,
            warning = WarningDark,
            warningContainer = WarningContainerDark,
            info = InfoDark,
            infoContainer = InfoContainerDark,
            achievementAmber = AchievementAmber,
            achievementAmberContainer = AchievementAmberDark,
            orbCore = OrbCoreDark,
            orbGlow = OrbGlowDark,
            isDark = true
        )
    } else {
        UnSmokeColors(
            success = SuccessLight,
            successContainer = SuccessContainerLight,
            warning = WarningLight,
            warningContainer = WarningContainerLight,
            info = InfoLight,
            infoContainer = InfoContainerLight,
            achievementAmber = AchievementAmber,
            achievementAmberContainer = AchievementAmberLight,
            orbCore = OrbCoreLight,
            orbGlow = OrbGlowLight,
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

// Convenience extension
val MaterialTheme.unSmokeColors: UnSmokeColors
    @Composable get() = LocalUnSmokeColors.current
