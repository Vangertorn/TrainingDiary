package com.yankin.membership.impl.domain.usecases

import com.yankin.membership.api.models.MembershipDomain
import com.yankin.membership.api.usecases.GetMembershipByIdUseCase
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import javax.inject.Inject

internal class GetMembershipByIdUseCaseImpl @Inject constructor(
    private val membershipRepository: MembershipRepository,
) : GetMembershipByIdUseCase {
    override suspend fun invoke(membershipId: Long): MembershipDomain {
        return membershipRepository.getMembershipById(membershipId)
    }
}