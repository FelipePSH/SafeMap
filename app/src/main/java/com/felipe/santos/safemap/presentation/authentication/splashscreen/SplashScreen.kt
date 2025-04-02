package com.felipe.santos.safemap.presentation.authentication.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felipe.santos.safemap.presentation.navigation.AccessDenied
import com.felipe.santos.safemap.presentation.navigation.Splash
import androidx.compose.runtime.getValue
import com.felipe.santos.safemap.presentation.navigation.SafeMap

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is SplashUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        is SplashUiState.Approved -> {
            LaunchedEffect(true) {
                navController.navigate(SafeMap) {
                    popUpTo(Splash) { inclusive = true }
                }
            }
        }

        is SplashUiState.NotApproved -> {
            LaunchedEffect(true) {
                navController.navigate(AccessDenied) {
                    popUpTo(Splash) { inclusive = true }
                }
            }
        }

        is SplashUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Something went wrong",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}