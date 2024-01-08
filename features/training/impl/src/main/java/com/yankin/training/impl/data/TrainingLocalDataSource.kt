package com.yankin.training.impl.data

import com.yankin.room.dao.TrainingDao
import com.yankin.room.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TrainingLocalDataSource @Inject constructor(
    private val db: TrainingDao
) {

    fun getTrainingDeletedFalseAscFlow(flags: Boolean): Flow<List<TrainingEntity>?> {
        return db.getTrainingDeletedFalseAscFlow(flags)
    }

    fun getTrainingDeletedFalseDescFlow(flags: Boolean): Flow<List<TrainingEntity>?> {
        return db.getTrainingDeletedFalseDescFlow(flags)
    }

    fun getTrainingPositions(): MutableList<Int>? {
        return db.getTrainingPositions()
    }

    fun insertTraining(trainingDomainEntity: TrainingEntity): Long {
        return db.insertTraining(trainingDomainEntity)
    }

    fun updateTraining(trainingDomainEntity: TrainingEntity) {
        db.updateTraining(trainingDomainEntity)
    }

    fun deletedTrainingFlags(trainingDomainEntity: TrainingEntity) {
        db.deletedTrainingFlags(trainingDomainEntity)
    }

    fun getTraining(id: Long): TrainingEntity {
        return db.getTraining(id)
    }

    fun deletedTrainingsByFlag(flag: Boolean) {
        db.deletedTrainingsByFlag(flag)
    }
}