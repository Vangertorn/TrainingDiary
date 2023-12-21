package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.usecases.DeleteTrainingByFlagsUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class DeleteTrainingByFlagsUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : DeleteTrainingByFlagsUseCase {

    override suspend fun invoke() {
        trainingRepository.deleteTrainingsByFlags()
    }
}