package com.yankin.training_block.api.usecases

import com.yankin.training_block.api.models.TrainingBlockDomain
import kotlinx.coroutines.flow.Flow

interface GetTrainingBlockListStreamUseCase {

    fun invoke(trainingId: Long): Flow<List<TrainingBlockDomain>>
}