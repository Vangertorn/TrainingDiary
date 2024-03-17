package com.yankin.training_block.api.usecases

import com.yankin.training_block.api.models.TrainingBlockDomain

interface SaveTrainingBlockUseCase {
    suspend fun invoke(trainingBlock: TrainingBlockDomain)
}