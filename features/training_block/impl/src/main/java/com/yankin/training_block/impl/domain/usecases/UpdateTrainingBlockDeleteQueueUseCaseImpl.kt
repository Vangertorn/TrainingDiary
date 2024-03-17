package com.yankin.training_block.impl.domain.usecases

import com.yankin.training_block.api.usecases.UpdateTrainingBlockDeleteQueueUseCase
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import javax.inject.Inject

internal class UpdateTrainingBlockDeleteQueueUseCaseImpl @Inject constructor(
    private val trainingBlockRepository: TrainingBlockRepository,
) : UpdateTrainingBlockDeleteQueueUseCase {

    override suspend fun invoke(trainingBlockId: Long, addToDeleteQueue: Boolean) {
        trainingBlockRepository.updateTrainingBlockDeleteQueue(
            trainingBlockId = trainingBlockId,
            addToDeleteQueue = addToDeleteQueue
        )
    }
}