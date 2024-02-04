package com.yankin.membership.api.usecases

import com.yankin.membership.api.models.MembershipDomain

interface GetMembershipByIdUseCase {
    suspend fun invoke(membershipId: Long): MembershipDomain
}