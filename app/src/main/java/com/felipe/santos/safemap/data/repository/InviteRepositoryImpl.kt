package com.felipe.santos.safemap.data.repository

import com.felipe.santos.safemap.domain.repository.InviteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InviteRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : InviteRepository {

    override suspend fun verifyInviteCode(code: String): Boolean {
        return try {
            val snapshot = firestore
                .collection("invite_codes")
                .document(code)
                .get()
                .await()

            snapshot.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}