package com.felipe.santos.safemap.domain.usecase

import com.felipe.santos.safemap.domain.model.AlertModel
import com.felipe.santos.safemap.domain.repository.AlertRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredAlertsUseCase @Inject constructor(
    private val repository: AlertRepository
) {
    suspend operator fun invoke(
        onlyVerified: Boolean = false,
        fromTimestamp: Timestamp? = null,
        toTimestamp: Timestamp? = null
    ): Flow<List<AlertModel>> {
        return repository.getAlertsStream().map { alerts ->
            val defaultFrom = fromTimestamp ?: Timestamp.now().let {
                Timestamp(it.seconds - 15L * 24 * 60 * 60, it.nanoseconds)
            }
            alerts
                .filter { if (onlyVerified) it.confirmations > 0 else true }
                .filter { it.timestamp >= defaultFrom }
                .filter { toTimestamp == null || it.timestamp <= toTimestamp }
        }
    }
}