package com.felipe.santos.safemap.domain.repository

import com.felipe.santos.safemap.domain.model.AlertModel
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    suspend fun getAlertsStream(): Flow<List<AlertModel>>

    suspend fun confirmAlert(alertId: String, userId: String)

    suspend fun hasUserConfirmed(alertId: String, userId: String): Boolean

    suspend fun addAlert(alert: AlertModel)

}