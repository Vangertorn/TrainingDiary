package com.yankin.membership.impl.domain.usecases

import com.yankin.membership.api.models.MembershipDomain
import com.yankin.membership.api.usecases.AddMembershipUseCase
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import javax.inject.Inject

internal class AddMembershipUseCaseImpl @Inject constructor(
    private val membershipRepository: MembershipRepository,
) : AddMembershipUseCase {
    override suspend fun invoke(membership: MembershipDomain) {
        return membershipRepository.addMembership(membership = membership)
    }
}