package com.felipe.santos.safemap.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.felipe.santos.safemap.presentation.authentication.accessdenied.AccessDeniedScreen
import com.felipe.santos.safemap.presentation.home.HomeScreen
import com.felipe.santos.safemap.presentation.authentication.splashscreen.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Home

@Serializable
object AccessDenied

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
        composable <Home>{
            HomeScreen()
        }
    }
}