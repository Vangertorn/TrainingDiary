package com.yankin.membership.api.usecases

import com.yankin.membership.api.models.MembershipDomain

interface AddMembershipUseCase {
    suspend fun invoke(membership: MembershipDomain)
}