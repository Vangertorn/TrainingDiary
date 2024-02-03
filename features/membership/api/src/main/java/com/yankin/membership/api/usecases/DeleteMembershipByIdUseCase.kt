package com.yankin.membership.api.usecases

interface DeleteMembershipByIdUseCase {
    suspend fun invoke(membershipId: Long)
}