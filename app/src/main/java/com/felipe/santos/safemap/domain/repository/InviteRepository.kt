package com.felipe.santos.safemap.domain.repository

import com.google.firebase.auth.FirebaseUser

interface InviteRepository {
    suspend fun verifyInviteCode(code: String): Boolean

    suspend fun approveUserWithCode(code: String, user: FirebaseUser): Result<Unit>
}