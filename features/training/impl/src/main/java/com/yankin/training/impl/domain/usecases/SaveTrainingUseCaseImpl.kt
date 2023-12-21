package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.SaveTrainingUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class SaveTrainingUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : SaveTrainingUseCase {

    override suspend fun invoke(training: TrainingDomain) {
        trainingRepository.saveTraining(training)
    }
}