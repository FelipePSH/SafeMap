package com.felipe.santos.safemap.domain.repository

interface InviteRepository {
    suspend fun verifyInviteCode(code: String): Boolean
}