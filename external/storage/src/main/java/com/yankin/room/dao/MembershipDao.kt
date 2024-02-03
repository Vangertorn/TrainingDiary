package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.yankin.room.entity.MembershipEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MembershipDao {

    @Insert
    abstract fun addMembership(entity: MembershipEntity): Long

    @Query("DELETE FROM table_membership WHERE id = :entityId")
    abstract fun deleteMembership(entityId: Long)

    @Query("SELECT * FROM table_membership WHERE endDate >= :currentDate or trainingCount != null and trainingCount > trainingCountPast LIMIT 1")
    abstract fun getActiveMembership(currentDate: Long): Flow<MembershipEntity?>

    @Transaction
    open fun addTrainingId(
        exerciseId: Long, membershipId: Long
    ) {
        val membershipEntity = getAccountedTrainingIdList(membershipId = membershipId)
        membershipEntity?.let {
            val resultList = membershipEntity.accountedTrainingIdList.toMutableList()
            resultList.add(exerciseId)
            updateAccountedTrainingIdList(accountedTrainingIdList = resultList, membershipId = membershipId)
        }
    }

    @Transaction
    open fun deleteTrainingId(
        exerciseId: Long, membershipId: Long
    ) {
        val membershipEntity = getAccountedTrainingIdList(membershipId = membershipId)
        membershipEntity?.let {
            val resultList = membershipEntity.accountedTrainingIdList.toMutableList()
            resultList.remove(exerciseId)
            updateAccountedTrainingIdList(accountedTrainingIdList = resultList, membershipId = membershipId)
        }
    }

    @Query("SELECT * FROM table_membership WHERE id == :membershipId LIMIT 1")
    abstract fun getAccountedTrainingIdList(membershipId: Long): MembershipEntity?

    @Query("UPDATE table_membership SET accountedTrainingIdList =:accountedTrainingIdList WHERE id = :membershipId")
    abstract fun updateAccountedTrainingIdList(accountedTrainingIdList: List<Long>, membershipId: Long)
}
