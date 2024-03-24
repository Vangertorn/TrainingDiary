package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.usecases.UpdateTrainingDeleteQueueUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class UpdateTrainingDeleteQueueUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : UpdateTrainingDeleteQueueUseCase {

    override suspend fun invoke(trainingId: Long, addToDeleteQueue: Boolean) {
        trainingRepository.updateTrainingBlockDeleteQueue(trainingId = trainingId, addToDeleteQueue = addToDeleteQueue)
    }
}