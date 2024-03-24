package com.yankin.training.api.usecases

interface UpdateTrainingDeleteQueueUseCase {

    suspend fun invoke(trainingId: Long, addToDeleteQueue: Boolean)
}