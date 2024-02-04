package com.yankin.membership.impl.domain.repositories

import com.yankin.membership.api.models.MembershipDomain
import kotlinx.coroutines.flow.Flow

interface MembershipRepository {

    val activeMembershipState: Flow<MembershipDomain?>

    suspend fun deleteTrainingId(trainingId: Long, membershipId: Long)

    suspend fun addTrainingId(trainingId: Long, membershipId: Long?)

    suspend fun addMembership(membership: MembershipDomain)

    suspend fun deleteMembership(membershipId: Long)

    suspend fun getMembershipById(membershipId: Long): MembershipDomain
}