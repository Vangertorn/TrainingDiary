package com.yankin.membership.api.usecases

interface AddTrainingIdFromMembershipUseCase {
    suspend fun invoke(trainingId: Long, membershipId: Long?)
}