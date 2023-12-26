package com.yankin.approach.impl.data

import com.yankin.room.dao.ApproachDao
import com.yankin.room.entity.ApproachEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ApproachLocalDataSource @Inject constructor(
    private val db: ApproachDao
) {

    fun insertApproach(approach: ApproachEntity) {
        db.insertApproach(approach)
    }

    fun deleteApproach(approach: ApproachEntity) {
        db.deleteApproach(approach)
    }

    fun getApproachListStream(exerciseId: Long): Flow<List<ApproachEntity>?> {
        return db.getApproachListStream(exerciseId)
    }

    fun getApproachList(exerciseId: Long): List<ApproachEntity>? {
        return db.getApproachList(exerciseId)
    }
}