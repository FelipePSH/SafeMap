package com.felipe.santos.safemap.domain.usecase

import com.felipe.santos.safemap.domain.repository.InviteRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class ApproveUserWithInviteCodeUseCase @Inject constructor(
    private val inviteRepository: InviteRepository
) {
    suspend operator fun invoke(inviteCode: String, currentUser: FirebaseUser): Result<Unit> {
        return inviteRepository.approveUserWithCode(inviteCode, currentUser)
    }
}