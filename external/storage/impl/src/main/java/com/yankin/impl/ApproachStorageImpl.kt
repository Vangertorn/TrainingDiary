package com.yankin.impl

import com.yankin.models.ApproachDomain
import com.yankin.room.converters.toEntity
import com.yankin.room.dao.ApproachDao
import com.yankin.storage.ApproachStorage

class ApproachStorageImpl(
    private val db: ApproachDao
) : ApproachStorage {

    override suspend fun insertApproach(approachDomain: ApproachDomain) {
        db.insertApproach(approachDomain.toEntity())
    }

    override suspend fun deleteApproach(approachDomain: ApproachDomain) {
        db.deleteApproach(approachDomain.toEntity())
    }
}