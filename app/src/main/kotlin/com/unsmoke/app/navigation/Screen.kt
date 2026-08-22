package com.unsmoke.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Craving : Screen("craving")
    object NRT : Screen("nrt")
    object Settings : Screen("settings")
}
