package com.yankin.training.impl.data

import com.yankin.room.dao.TrainingDao
import com.yankin.room.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TrainingLocalDataSource @Inject constructor(
    private val db: TrainingDao
) {

    fun getTrainingDeletedFalseAscFlow(): Flow<List<TrainingEntity>?> {
        return db.getTrainingDeletedFalseAscFlow()
    }

    fun getTrainingDeletedFalseDescFlow(): Flow<List<TrainingEntity>?> {
        return db.getTrainingDeletedFalseDescFlow()
    }

    fun insertTraining(trainingDomainEntity: TrainingEntity): Long {
        return db.insertTraining(trainingDomainEntity)
    }

    fun updateTraining(
        trainingId: Long,
        trainingDate: Long,
        comment: String?,
        personWeight: Double?,
        trainingMuscleGroupIds: List<Long>
    ) {
        db.updateTraining(
            trainingId = trainingId,
            trainingDate = trainingDate,
            comment = comment,
            personWeight = personWeight,
            trainingMuscleGroupIds = trainingMuscleGroupIds,
        )
    }

    fun getTraining(id: Long): TrainingEntity {
        return db.getTraining(id)
    }

    fun updateTrainingBlockDeleteQueue(trainingId: Long, addToDeleteQueue: Boolean) {
        if (addToDeleteQueue) {
            db.addToDeleteQueue(trainingId)
        } else {
            db.removeFromDeleteQueue(trainingId)
        }
    }

    fun clearDeleteQueue() {
        db.clearDeleteQueue()
    }
}