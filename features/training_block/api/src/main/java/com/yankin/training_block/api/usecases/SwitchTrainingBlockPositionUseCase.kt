package com.yankin.training_block.api.usecases

interface SwitchTrainingBlockPositionUseCase {

    suspend fun invoke(firstTrainingBlockId: Long, secondTrainingBlockId: Long)
}