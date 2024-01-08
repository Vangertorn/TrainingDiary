package com.yankin.impl

import com.yankin.models.SuperSetDomain
import com.yankin.models.info.SuperSetInfoDomain
import com.yankin.exercise.impl.data.toEntity
import com.yankin.exercise.impl.data.toModel
import com.yankin.room.dao.SuperSetDao
import com.yankin.storage.SuperSetStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SuperSetStorageImpl(
    private val db: SuperSetDao
) : SuperSetStorage {
    override fun getSuperSetInfoByTrainingIdAndFlagsFlow(
        id: Long,
        flags: Boolean,
        visibility: Boolean
    ): Flow<List<SuperSetInfoDomain>> {
        return db.getSuperSetInfoByTrainingIdAndFlagsFlow(id, flags, visibility).map {
            it.map {
                it.toModel()
            }
        }
    }

    override fun getSuperSetById(idSuperSet: Long): SuperSetDomain {
        return db.getSuperSetById(idSuperSet).toModel()
    }

    override fun updateSuperSet(superSetDomainEntity: SuperSetDomain) {
        db.updateSuperSet(com.yankin.exercise.impl.data.toEntity())
    }

    override fun deletedSuperSetByVisible() {
        db.deletedSuperSetByVisible()
    }

    override fun insertSuperSet(superSetDomainEntity: SuperSetDomain): Long {
        return db.insertSuperSet(com.yankin.exercise.impl.data.toEntity())
    }
}