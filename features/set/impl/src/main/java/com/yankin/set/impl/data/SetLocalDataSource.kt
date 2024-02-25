package com.yankin.set.impl.data

import com.yankin.room.dao.SetDao
import com.yankin.room.entity.SetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SetLocalDataSource @Inject constructor(
    private val db: SetDao
) {

    fun insertSet(set: SetEntity) {
        db.insertSet(set)
    }

    fun deleteSetById(setId: Long) {
        db.deleteSetById(setId)
    }

    fun getSetListStream(exerciseId: Long): Flow<List<SetEntity>?> {
        return db.getSetListStream(exerciseId)
    }

    fun getSetList(exerciseId: Long): List<SetEntity>? {
        return db.getSetList(exerciseId)
    }
}