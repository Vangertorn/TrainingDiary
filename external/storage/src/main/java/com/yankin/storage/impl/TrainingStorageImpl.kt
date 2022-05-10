package com.yankin.storage.impl

import com.yankin.models.TrainingDomain
import com.yankin.models.info.ViewHolderTypesDomain
import com.yankin.storage.TrainingStorage
import com.yankin.storage.room.converters.toEntity
import com.yankin.storage.room.converters.toModel
import com.yankin.storage.room.dao.TrainingDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingStorageImpl(
    private val db: TrainingDao
) : TrainingStorage {
    override fun getExercisesInfoByTrainingIdAndFlagsFlow(
        idTraining: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypesDomain.ExerciseInfoDomain>> {
        return db.getExercisesInfoByTrainingIdAndFlagsFlow(idTraining, flags).map {
            it.map {
                it.toModel()
            }
        }
    }

    override fun getTrainingDeletedFalseAscFlow(flags: Boolean): Flow<List<TrainingDomain>?> {
        return db.getTrainingDeletedFalseAscFlow(flags).map {
            it?.map {
                it.toModel()
            }
        }
    }

    override fun getTrainingDeletedFalseDescFlow(flags: Boolean): Flow<List<TrainingDomain>?> {
        return db.getTrainingDeletedFalseDescFlow(flags).map {
            it?.map {
                it.toModel()
            }
        }
    }

    override fun getTrainingPositions(): MutableList<Int>? {
        return db.getTrainingPositions()
    }

    override fun insertTraining(trainingDomainEntity: TrainingDomain): Long {
        return db.insertTraining(trainingDomainEntity.toEntity())
    }

    override fun updateTraining(trainingDomainEntity: TrainingDomain) {
        db.updateTraining(trainingDomainEntity.toEntity())
    }

    override fun deletedTrainingFlags(trainingDomainEntity: TrainingDomain) {
        db.deletedTrainingFlags(trainingDomainEntity.toEntity())
    }

    override fun getTraining(id: Long): TrainingDomain {
        return db.getTraining(id).toModel()
    }

    override fun deletedTrainingsByFlags(flags: Boolean) {
        db.deletedTrainingsByFlags(flags)
    }
}