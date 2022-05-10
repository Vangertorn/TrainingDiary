package com.yankin.storage.impl

import com.yankin.models.ApproachDomain
import com.yankin.storage.ApproachStorage
import com.yankin.storage.room.converters.toEntity
import com.yankin.storage.room.dao.ApproachDao

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