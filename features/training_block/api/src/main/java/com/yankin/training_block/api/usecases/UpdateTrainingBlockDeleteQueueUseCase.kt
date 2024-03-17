package com.yankin.training_block.api.usecases

interface UpdateTrainingBlockDeleteQueueUseCase {

    suspend fun invoke(trainingBlockId: Long, addToDeleteQueue: Boolean)
}