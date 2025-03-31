package com.felipe.santos.safemap.presentation.splashscreen

sealed class SplashUiState {
    object Loading : SplashUiState()
    object Approved : SplashUiState()
    object NotApproved : SplashUiState()
    data class Error(val message: String) : SplashUiState()
}