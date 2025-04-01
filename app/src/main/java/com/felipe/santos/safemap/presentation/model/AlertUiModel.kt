package com.felipe.santos.safemap.presentation.model

import com.felipe.santos.safemap.domain.model.AlertType

data class AlertUiModel(
    val type: AlertType,
    val description: String,
    val timestampFormatted: String,
    val latitude: Double,
    val longitude: Double,
    val confirmations: Int
)
