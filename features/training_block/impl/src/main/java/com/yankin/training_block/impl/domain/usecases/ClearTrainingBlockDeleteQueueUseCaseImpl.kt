package com.yankin.training_block.impl.domain.usecases

import com.yankin.training_block.api.usecases.ClearTrainingBlockDeleteQueueUseCase
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import javax.inject.Inject

internal class ClearTrainingBlockDeleteQueueUseCaseImpl @Inject constructor(
    private val trainingBlockRepository: TrainingBlockRepository,
) : ClearTrainingBlockDeleteQueueUseCase {

    override suspend fun invoke() {
        trainingBlockRepository.clearDeleteQueue()
    }
}