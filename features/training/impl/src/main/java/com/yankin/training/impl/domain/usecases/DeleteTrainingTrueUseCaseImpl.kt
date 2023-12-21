package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.DeleteTrainingTrueUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class DeleteTrainingTrueUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : DeleteTrainingTrueUseCase {

    override suspend fun invoke(training: TrainingDomain) {
        trainingRepository.deletedTrainingTrue(training)
    }
}