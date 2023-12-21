package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class GetTrainingByIdUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : GetTrainingByIdUseCase {

    override suspend fun invoke(trainingId: Long): TrainingDomain {
        return trainingRepository.getTrainingById(trainingId)
    }
}