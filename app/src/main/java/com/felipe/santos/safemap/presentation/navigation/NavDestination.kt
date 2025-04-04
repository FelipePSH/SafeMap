package com.felipe.santos.safemap.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.felipe.santos.safemap.presentation.alerts.AlertsScreen
import com.felipe.santos.safemap.presentation.authentication.accessdenied.AccessDeniedScreen
import com.felipe.santos.safemap.presentation.authentication.splashscreen.SplashScreen
import com.felipe.santos.safemap.presentation.emergency.EmergencyScreen
import com.felipe.santos.safemap.presentation.home.SafeMapScreen
import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object SafeMap

@Serializable
object AccessDenied

@Serializable
object Alerts

@Serializable
object Emergency

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController:  NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier
    ) {
        composable <Splash> {
            SplashScreen(navController = navController)
        }
        composable <AccessDenied>{
            AccessDeniedScreen(navController)
        }
        composable <SafeMap>{
            SafeMapScreen()
        }

        composable<Alerts> {
            AlertsScreen()
        }

        composable<Emergency> {
            EmergencyScreen()
        }

    }
}