package com.yankin.training.api.usecases

import com.yankin.training.api.models.TrainingDomain

interface DeleteTrainingFalseUseCase {

    suspend fun invoke(training: TrainingDomain)
}