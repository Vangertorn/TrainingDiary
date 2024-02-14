package com.yankin.super_set.impl.data

import com.yankin.room.dao.SuperSetDao
import com.yankin.room.entity.SuperSetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SuperSetLocalDataSource @Inject constructor(
    private val db: SuperSetDao
) {
    fun getSuperSetListByTrainingIdStream(id: Long): Flow<List<SuperSetEntity>> {
        return db.getSuperSetListByTrainingIdFlow(id = id, flags = false)
    }

    fun getSuperSetById(superSetId: Long): SuperSetEntity {
        return db.getSuperSetById(idSuperSet = superSetId)
    }

    fun updateSuperSetDeleted(superSetId: Long, deleted: Boolean) {
        db.updateSuperSetDeleted(superSetId = superSetId, deleted = deleted)
    }

    fun insertSuperSet(superSet: SuperSetEntity): Long {
        return db.insertSuperSet(superSetEntity = superSet)
    }
}