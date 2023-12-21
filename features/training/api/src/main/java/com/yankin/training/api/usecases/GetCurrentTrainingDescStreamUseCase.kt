package com.yankin.training.api.usecases

import com.yankin.training.api.models.TrainingDomain
import kotlinx.coroutines.flow.Flow

interface GetCurrentTrainingDescStreamUseCase {
    fun invoke(): Flow<List<TrainingDomain>>
}