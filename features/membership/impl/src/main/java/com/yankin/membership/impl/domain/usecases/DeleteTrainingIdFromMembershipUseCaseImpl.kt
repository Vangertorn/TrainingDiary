package com.yankin.membership.impl.domain.usecases

import com.yankin.membership.api.usecases.DeleteTrainingIdFromMembershipUseCase
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import javax.inject.Inject

internal class DeleteTrainingIdFromMembershipUseCaseImpl @Inject constructor(
    private val membershipRepository: MembershipRepository,
) : DeleteTrainingIdFromMembershipUseCase {
    override suspend fun invoke(trainingId: Long, membershipId: Long) {
        return membershipRepository.deleteTrainingId(trainingId = trainingId, membershipId = membershipId)
    }
}