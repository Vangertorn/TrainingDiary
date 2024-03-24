package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.UpdateTrainingUseCase
import com.yankin.training.impl.data.toModel
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class UpdateTrainingUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : UpdateTrainingUseCase {

    override suspend fun invoke(training: TrainingDomain) {
        trainingRepository.updateTraining(training.toModel())
    }
}