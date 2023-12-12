package com.yankin.storage

import com.yankin.models.TrainingDomain
import com.yankin.models.info.ViewHolderTypesDomain
import kotlinx.coroutines.flow.Flow

interface TrainingStorage {

    fun getExercisesInfoByTrainingIdAndFlagsFlow(
        idTraining: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypesDomain.ExerciseInfoDomain>>

    fun getTrainingDeletedFalseAscFlow(flags: Boolean): Flow<List<TrainingDomain>?>

    fun getTrainingDeletedFalseDescFlow(flags: Boolean): Flow<List<TrainingDomain>?>

    fun getTrainingPositions(): MutableList<Int>?

    fun insertTraining(trainingDomainEntity: TrainingDomain): Long

    fun updateTraining(trainingDomainEntity: TrainingDomain)

    fun deletedTrainingFlags(trainingDomainEntity: TrainingDomain)

    fun getTraining(id: Long): TrainingDomain

    fun deletedTrainingsByFlags(flags: Boolean)
}