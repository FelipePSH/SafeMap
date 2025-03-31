package com.felipe.santos.safemap.presentation.splashscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felipe.santos.safemap.presentation.navigation.AccessDenied
import com.felipe.santos.safemap.presentation.navigation.Home
import com.felipe.santos.safemap.presentation.navigation.Splash
import androidx.compose.runtime.getValue

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is SplashUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Companion.Center) {
                CircularProgressIndicator()
            }
        }
        is SplashUiState.Approved -> {
            LaunchedEffect(Unit) {
                navController.navigate(Home) {
                    popUpTo(Splash) { inclusive = true }
                }
            }
        }
        is SplashUiState.NotApproved -> {
            LaunchedEffect(Unit) {
                navController.navigate(AccessDenied) {
                    popUpTo(Splash) { inclusive = true }
                }
            }
        }
        is SplashUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Something went wrong")
            }
        }
    }
}