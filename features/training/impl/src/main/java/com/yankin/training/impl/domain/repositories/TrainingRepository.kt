package com.yankin.training.impl.domain.repositories

import com.yankin.training.api.models.TrainingDomain
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    val currentTrainingAscFlow: Flow<List<TrainingDomain>>

    val currentTrainingDescFlow: Flow<List<TrainingDomain>>

    suspend fun saveTraining(training: TrainingDomain)

    suspend fun updateTraining(training: TrainingDomain)

    suspend fun deletedTrainingTrue(training: TrainingDomain)

    suspend fun deletedTrainingFalse(training: TrainingDomain)

    suspend fun getTrainingById(trainingId: Long): TrainingDomain

    suspend fun deleteTrainingsByFlags()

    suspend fun forgotIdTraining()
}