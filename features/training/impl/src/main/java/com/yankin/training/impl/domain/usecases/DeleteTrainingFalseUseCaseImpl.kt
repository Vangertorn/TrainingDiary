package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.DeleteTrainingFalseUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class DeleteTrainingFalseUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : DeleteTrainingFalseUseCase {

    override suspend fun invoke(training: TrainingDomain) {
        trainingRepository.deletedTrainingFalse(training)
    }
}