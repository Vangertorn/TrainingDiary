package com.yankin.membership.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.membership.api.models.MembershipDomain
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

internal class MembershipRepositoryImpl @Inject constructor(
    private val membershipLocalDataSource: MembershipLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : MembershipRepository {

    override val activeMembershipState: Flow<MembershipDomain?> =
        membershipLocalDataSource.getActiveMembership(Calendar.getInstance().time.time).map { entity ->
            entity?.toDomain()
        }

    override suspend fun deleteTrainingId(trainingId: Long, membershipId: Long) {
        withContext(coroutineDispatchers.io) {
            membershipLocalDataSource.deleteTrainingId(trainingId = trainingId, membershipId = membershipId)
        }
    }

    override suspend fun addTrainingId(trainingId: Long, membershipId: Long?) {
        withContext(coroutineDispatchers.io) {
            membershipLocalDataSource.addTrainingId(trainingId = trainingId, membershipId = membershipId)
        }
    }

    override suspend fun addMembership(membership: MembershipDomain) {
        withContext(coroutineDispatchers.io) {
            membershipLocalDataSource.addMembership(membership = membership.toEntity())
        }
    }

    override suspend fun deleteMembership(membershipId: Long) {
        withContext(coroutineDispatchers.io) {
            membershipLocalDataSource.deleteMembership(membershipId = membershipId)
        }
    }

    override suspend fun getMembershipById(membershipId: Long): MembershipDomain =
        withContext(coroutineDispatchers.io) {
            return@withContext membershipLocalDataSource.getMembershipById(membershipId = membershipId).toDomain()
        }
}