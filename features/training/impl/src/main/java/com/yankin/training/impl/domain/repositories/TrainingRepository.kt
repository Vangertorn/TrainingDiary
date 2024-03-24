package com.yankin.training.impl.domain.repositories

import com.yankin.training.impl.domain.models.TrainingModel
import kotlinx.coroutines.flow.Flow

internal interface TrainingRepository {

    val currentTrainingAscStream: Flow<List<TrainingModel>>

    val currentTrainingDescStream: Flow<List<TrainingModel>>

    suspend fun saveTraining(training: TrainingModel): Long

    suspend fun updateTraining(training: TrainingModel)

    suspend fun getTrainingById(trainingId: Long): TrainingModel

    suspend fun updateTrainingBlockDeleteQueue(trainingId: Long, addToDeleteQueue: Boolean)

    suspend fun clearDeleteQueue()
}