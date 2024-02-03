package com.yankin.membership.impl.domain.usecases

import com.yankin.membership.api.usecases.DeleteMembershipByIdUseCase
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import javax.inject.Inject

internal class DeleteMembershipByIdUseCaseImpl @Inject constructor(
    private val membershipRepository: MembershipRepository,
) : DeleteMembershipByIdUseCase {
    override suspend fun invoke(membershipId: Long) {
        membershipRepository.deleteMembership(membershipId)
    }
}