package com.yankin.membership.api.usecases

import com.yankin.membership.api.models.MembershipDomain
import kotlinx.coroutines.flow.Flow

interface GetActiveMembershipStreamUseCase {
    fun invoke(): Flow<MembershipDomain?>
}