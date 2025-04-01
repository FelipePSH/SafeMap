package com.felipe.santos.safemap.presentation.model

data class AlertUiModel(
    val title: String,
    val description: String,
    val timestampFormatted: String,
    val latitude: Double,
    val longitude: Double,
    val confirmations: Int
)
