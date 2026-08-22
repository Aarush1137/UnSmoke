package com.unsmoke.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unsmoke.app.feature.home.HomeScreen
import com.unsmoke.app.feature.onboarding.OnboardingScreen
import com.unsmoke.app.feature.craving.CravingScreen
import com.unsmoke.app.feature.craving.CravingTimerScreen
import com.unsmoke.app.feature.craving.CravingOutcomeScreen
import com.unsmoke.app.feature.nrt.NRTDashboardScreen
import com.unsmoke.app.feature.progress.ProgressScreen
import com.unsmoke.app.feature.checkin.CheckInScreen
import com.unsmoke.app.feature.profile.ProfileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onCravingClick = { navController.navigate(Screen.Craving.route) },
                onProgressClick = { navController.navigate(Screen.Progress.route) },
                onNRTClick = { navController.navigate(Screen.NRT.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onCheckInClick = { navController.navigate(Screen.CheckIn.route) }
            )
        }
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Craving.route) {
            CravingScreen(
                onTimerStart = { navController.navigate("craving_timer") },
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = "craving_timer") {
            CravingTimerScreen(
                onDefeated = { navController.navigate("craving_outcome") },
                onSmoked = { navController.navigate(Screen.Home.route) }
            )
        }
        composable(route = "craving_outcome") {
            CravingOutcomeScreen(
                onContinue = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.NRT.route) {
            NRTDashboardScreen(onBack = { navController.popBackStack() })
        }
        composable(route = Screen.Progress.route) {
            ProgressScreen(onInsightsClick = {}, onBack = { navController.popBackStack() })
        }
        composable(route = Screen.CheckIn.route) {
            CheckInScreen(onComplete = { navController.popBackStack() })
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(onPlanClick = {}, onAchievementsClick = {}, 
                onBack = { navController.popBackStack() },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
    }
}
