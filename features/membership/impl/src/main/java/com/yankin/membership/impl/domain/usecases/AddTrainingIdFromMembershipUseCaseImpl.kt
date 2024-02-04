package com.yankin.membership.impl.domain.usecases

import com.yankin.membership.api.usecases.AddTrainingIdFromMembershipUseCase
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import javax.inject.Inject

internal class AddTrainingIdFromMembershipUseCaseImpl @Inject constructor(
    private val membershipRepository: MembershipRepository,
) : AddTrainingIdFromMembershipUseCase {
    override suspend fun invoke(trainingId: Long, membershipId: Long?) {
        return membershipRepository.addTrainingId(trainingId = trainingId, membershipId = membershipId)
    }
}