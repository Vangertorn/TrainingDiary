package com.yankin.training_block.impl.domain.usecases

import com.yankin.exercise.api.repositories.ExerciseRepository
import com.yankin.training_block.api.models.TrainingBlockDomain
import com.yankin.training_block.api.usecases.SaveTrainingBlockUseCase
import com.yankin.training_block.impl.domain.TrainingBlockModel
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import javax.inject.Inject

internal class SaveTrainingBlockUseCaseImpl @Inject constructor(
    private val trainingBlockRepository: TrainingBlockRepository,
    private val exerciseRepository: ExerciseRepository,
) : SaveTrainingBlockUseCase {
    override suspend fun invoke(trainingBlock: TrainingBlockDomain) {
        val trainingBlockId = trainingBlockRepository.saveTrainingBlock(
            TrainingBlockModel(id = 0, trainingId = trainingBlock.trainingId, position = trainingBlock.position)
        )
        when (trainingBlock) {
            is TrainingBlockDomain.SingleExercise -> exerciseRepository.saveExercise(
                trainingBlock.exercise.copy(trainingBlockId = trainingBlockId)
            )
            is TrainingBlockDomain.SuperSet -> trainingBlock.exerciseList.forEach { exerciseDomain ->
                exerciseRepository.saveExercise(exerciseDomain.copy(trainingBlockId = trainingBlockId))
            }
        }
    }
}