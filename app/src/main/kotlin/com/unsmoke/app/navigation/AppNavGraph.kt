package com.unsmoke.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unsmoke.app.feature.home.HomeScreen
import com.unsmoke.app.feature.recovery.RecoveryScreen
import com.unsmoke.app.feature.splash.SplashScreen
import com.unsmoke.app.feature.onboarding.OnboardingScreen
import com.unsmoke.app.feature.craving.CravingScreen
import com.unsmoke.app.feature.craving.CravingTimerScreen
import com.unsmoke.app.feature.craving.CravingOutcomeScreen
import com.unsmoke.app.feature.nrt.NRTDashboardScreen
import com.unsmoke.app.feature.progress.ProgressScreen
import com.unsmoke.app.feature.checkin.CheckInScreen
import com.unsmoke.app.feature.journal.JournalScreen
import com.unsmoke.app.feature.profile.ProfileScreen
import com.unsmoke.app.feature.plan.PlanScreen
import com.unsmoke.app.feature.achievements.AchievementsScreen
import com.unsmoke.app.feature.insights.InsightsScreen
import com.unsmoke.app.feature.analytics.AnalyticsScreen
import com.unsmoke.app.feature.achievements.AchievementsScreen
import com.unsmoke.app.feature.settings.SettingsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(onTimeout = { onboardingComplete ->
                val destination = if (onboardingComplete) Screen.Home.route else Screen.Onboarding.route
                navController.navigate(destination) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                    onRelapseClick = { navController.navigate("recovery") },
                onCravingClick = { navController.navigate(Screen.Craving.route) },
                onProgressClick = { navController.navigate(Screen.Progress.route) },
                onNRTClick = { navController.navigate(Screen.NRT.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onCheckInClick = { navController.navigate(Screen.Journal.route) },
                onBuddyClick = { navController.navigate(Screen.Buddy.route) }
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
                onSmoked = { navController.navigate("recovery") }
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
        composable(route = "recovery") {
            RecoveryScreen(
                onComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.NRT.route) {
            NRTDashboardScreen(onBack = { navController.popBackStack() })
        }
                composable(route = Screen.Insights.route) { InsightsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(route = Screen.Analytics.route) { AnalyticsScreen(onBack = { navController.popBackStack() }) }
        composable(route = Screen.Progress.route) {
            ProgressScreen(onInsightsClick = { navController.navigate(Screen.Insights.route) }, onAnalyticsClick = { navController.navigate(Screen.Analytics.route) }, onBack = { navController.popBackStack() })
        }
        composable(route = Screen.Journal.route) {
            JournalScreen(onBack = { navController.popBackStack() }, onAddClick = { navController.navigate(Screen.CheckIn.route) })
        }

        composable(route = Screen.CheckIn.route) {
            CheckInScreen(onComplete = { navController.popBackStack() })
        }
        composable(route = Screen.Plan.route) { PlanScreen(onBack = { navController.popBackStack() }) }
        composable(route = Screen.Achievements.route) { AchievementsScreen(onBack = { navController.popBackStack() }) }
        composable(route = Screen.Buddy.route) { com.unsmoke.app.feature.buddy.BuddyScreen() }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onReset = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(onPlanClick = { navController.navigate(Screen.Plan.route) }, onAchievementsClick = { navController.navigate(Screen.Achievements.route) }, 
                onBack = { navController.popBackStack() },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
    }
}




