package com.yankin.membership.impl.domain.usecases

import com.yankin.membership.api.models.MembershipDomain
import com.yankin.membership.api.usecases.GetActiveMembershipStreamUseCase
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetActiveMembershipStreamUseCaseImpl @Inject constructor(
    private val membershipRepository: MembershipRepository,
) : GetActiveMembershipStreamUseCase {
    override fun invoke(): Flow<MembershipDomain?> {
        return membershipRepository.activeMembershipState
    }
}