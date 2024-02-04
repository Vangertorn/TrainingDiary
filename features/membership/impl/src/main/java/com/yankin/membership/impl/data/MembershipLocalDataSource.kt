package com.yankin.membership.impl.data

import com.yankin.room.dao.MembershipDao
import com.yankin.room.entity.MembershipEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MembershipLocalDataSource @Inject constructor(
    private val db: MembershipDao
) {

    fun deleteTrainingId(trainingId: Long, membershipId: Long) {
        db.deleteTrainingId(exerciseId = trainingId, membershipId = membershipId)
    }

    fun addTrainingId(trainingId: Long, membershipId: Long?) {
        db.addTrainingId(exerciseId = trainingId, membershipId = membershipId)
    }

    fun getActiveMembership(currentDate: Long): Flow<MembershipEntity?> {
        return db.getActiveMembership(currentDate)
    }

    fun addMembership(membership: MembershipEntity) {
        db.addMembership(membership)
    }

    fun deleteMembership(membershipId: Long) {
        db.deleteMembership(membershipId)
    }

    fun getMembershipById(membershipId: Long): MembershipEntity {
        return db.getMembershipById(membershipId)
    }
}