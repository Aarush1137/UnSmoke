package com.unsmoke.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Craving : Screen("craving")
    object NRT : Screen("nrt")
    object Progress : Screen("progress")
    object Profile : Screen("profile")
    object CheckIn : Screen("checkin")
    object Journal : Screen("journal")
    object Settings : Screen("settings")
    object Plan : Screen("plan")
    object Achievements : Screen("achievements")
    object Insights : Screen("insights")
    object Analytics : Screen("analytics")
    object Buddy : Screen("buddy")
}

