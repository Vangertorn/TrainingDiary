package com.yankin.training.impl.domain.usecases

import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.GetCurrentTrainingAscStreamUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentTrainingAscStreamUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : GetCurrentTrainingAscStreamUseCase {
    override fun invoke(): Flow<List<TrainingDomain>> = trainingRepository.currentTrainingAscFlow
}