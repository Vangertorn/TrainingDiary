package com.yankin.training_block.impl.domain.repositories

import com.yankin.training_block.impl.domain.TrainingBlockModel
import kotlinx.coroutines.flow.Flow

internal interface TrainingBlockRepository {

    fun getTrainingBlockByTrainingIdStream(trainingId: Long): Flow<List<TrainingBlockModel>>

    fun getTrainingBlockByIdStream(trainingBlockId: Long): Flow<TrainingBlockModel>

    suspend fun saveTrainingBlock(trainingBlock: TrainingBlockModel): Long

    suspend fun clearDeleteQueue()

    suspend fun updateTrainingBlockDeleteQueue(trainingBlockId: Long, addToDeleteQueue: Boolean)

    suspend fun switchTrainingBlockPosition(
        firstTrainingBlockId: Long,
        secondTrainingBlockId: Long,
    )
}