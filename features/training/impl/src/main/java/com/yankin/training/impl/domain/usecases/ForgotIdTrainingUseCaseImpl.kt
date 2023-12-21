package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.usecases.ForgotIdTrainingUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class ForgotIdTrainingUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : ForgotIdTrainingUseCase {
    override suspend fun invoke() {
        trainingRepository.forgotIdTraining()
    }
}