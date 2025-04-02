package com.felipe.santos.safemap.data.repository

import com.felipe.santos.safemap.domain.model.AlertModel
import com.felipe.santos.safemap.domain.repository.AlertRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore
): AlertRepository {

    override suspend fun getAlertsStream(): Flow<List<AlertModel>> = callbackFlow {
        val listenerRegistration = firestore.collection("alerts")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val alerts = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(AlertModel::class.java)?.copy(id = doc.id)
                    }
                    trySend(alerts).isSuccess
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun confirmAlert(alertId: String, userId: String) {
        val alertRef = firestore.collection("alerts").document(alertId)
        val confirmationRef = alertRef.collection("confirmations").document(userId)

        val alreadyConfirmed = confirmationRef.get().await().exists()

        if (!alreadyConfirmed) {
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(alertRef)
                val current = snapshot.getLong("confirmations") ?: 0
                transaction.update(alertRef, "confirmations", current + 1)
                transaction.set(confirmationRef, mapOf("confirmedAt" to com.google.firebase.Timestamp.now()))
            }.await()
        }
    }

    override suspend fun hasUserConfirmed(alertId: String, userId: String): Boolean {
        val confirmationRef = firestore
            .collection("alerts")
            .document(alertId)
            .collection("confirmations")
            .document(userId)

        return confirmationRef.get().await().exists()
    }

    override suspend fun addAlert(alert: AlertModel) {
        firestore.collection("alerts")
            .add(alert)
            .await()
    }
}