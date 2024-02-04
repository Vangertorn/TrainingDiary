package com.yankin.training.impl.domain.repositories

import com.yankin.training.api.models.TrainingDomain
import kotlinx.coroutines.flow.Flow

internal interface TrainingRepository {

    val currentTrainingAscStream: Flow<List<TrainingDomain>>

    val currentTrainingDescStream: Flow<List<TrainingDomain>>

    suspend fun saveTraining(training: TrainingDomain): Long

    suspend fun updateTraining(training: TrainingDomain)

    suspend fun deletedTrainingTrue(training: TrainingDomain)

    suspend fun deletedTrainingFalse(training: TrainingDomain)

    suspend fun getTrainingById(trainingId: Long): TrainingDomain

    suspend fun deleteTrainingsByFlags()

    suspend fun forgotIdTraining()
}