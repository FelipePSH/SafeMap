package com.felipe.santos.safemap.data.repository

import com.felipe.santos.safemap.domain.repository.InviteRepository
import com.felipe.santos.safemap.utils.generateInviteCode
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
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

    override suspend fun approveUserWithCode(
        code: String,
        user: FirebaseUser
    ): Result<Unit> {
        return try {
            val codeDoc = firestore.collection("invite_codes").document(code).get().await()
            if (!codeDoc.exists()) return Result.failure(Exception("Invalid invite code"))

            val invitedBy = codeDoc.getString("owner_name") ?: "unknown"

            firestore.collection("approved_users").document(user.uid).set(
                mapOf(
                    "name" to user.uid,
                    "invited_by" to invitedBy,
                    "isWoman" to true,
                    "timeStamp" to FieldValue.serverTimestamp()
                )
            ).await()

            val newCode = generateInviteCode()
            firestore.collection("invite_codes").document(newCode).set(
                mapOf(
                    "owner_uid" to user.uid,
                    "owner_name" to user.uid,
                    "createdAt" to FieldValue.serverTimestamp()
                )
            ).await()

            Result.success(Unit)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}