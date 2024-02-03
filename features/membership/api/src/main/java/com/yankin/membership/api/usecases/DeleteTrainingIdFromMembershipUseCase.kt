package com.yankin.membership.api.usecases

interface DeleteTrainingIdFromMembershipUseCase {
    suspend fun invoke(trainingId: Long, membershipId: Long)
}