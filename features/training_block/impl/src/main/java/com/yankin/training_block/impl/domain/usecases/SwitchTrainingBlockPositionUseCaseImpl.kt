package com.yankin.training_block.impl.domain.usecases

import com.yankin.training_block.api.usecases.SwitchTrainingBlockPositionUseCase
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import javax.inject.Inject

internal class SwitchTrainingBlockPositionUseCaseImpl @Inject constructor(
    private val trainingBlockRepository: TrainingBlockRepository,
) : SwitchTrainingBlockPositionUseCase {

    override suspend fun invoke(firstTrainingBlockId: Long, secondTrainingBlockId: Long) {
        trainingBlockRepository.switchTrainingBlockPosition(
            firstTrainingBlockId = firstTrainingBlockId,
            secondTrainingBlockId = secondTrainingBlockId
        )
    }
}