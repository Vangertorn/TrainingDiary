package com.yankin.storage.impl

import com.yankin.models.SuperSetDomain
import com.yankin.models.info.SuperSetInfoDomain
import com.yankin.storage.SuperSetStorage
import com.yankin.storage.room.converters.toEntity
import com.yankin.storage.room.converters.toModel
import com.yankin.storage.room.dao.SuperSetDao
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
        db.updateSuperSet(superSetDomainEntity.toEntity())
    }

    override fun deletedSuperSetByVisible() {
        db.deletedSuperSetByVisible()
    }

    override fun insertSuperSet(superSetDomainEntity: SuperSetDomain): Long {
        return db.insertSuperSet(superSetDomainEntity.toEntity())
    }
}