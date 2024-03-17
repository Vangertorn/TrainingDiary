package com.yankin.training_block.api.usecases

import com.yankin.training_block.api.models.TrainingBlockDomain
import kotlinx.coroutines.flow.Flow

interface GetTrainingBlockByIdStreamUseCase {

    fun invoke(trainingBlockId: Long): Flow<TrainingBlockDomain>
}