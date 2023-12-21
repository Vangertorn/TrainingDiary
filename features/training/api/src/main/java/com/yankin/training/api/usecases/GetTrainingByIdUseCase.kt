package com.yankin.training.api.usecases

import com.yankin.training.api.models.TrainingDomain

interface GetTrainingByIdUseCase {

    suspend fun invoke(trainingId: Long): TrainingDomain
}