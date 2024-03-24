package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.usecases.ClearTrainingDeleteQueueUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class ClearTrainingDeleteQueueUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : ClearTrainingDeleteQueueUseCase {

    override suspend fun invoke() {
        trainingRepository.clearDeleteQueue()
    }
}