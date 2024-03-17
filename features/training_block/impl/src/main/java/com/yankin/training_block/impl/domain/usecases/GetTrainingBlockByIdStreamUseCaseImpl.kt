package com.yankin.training_block.impl.domain.usecases

import com.yankin.exercise.api.repositories.ExerciseRepository
import com.yankin.training_block.api.NotValidTrainingBlockDomain
import com.yankin.training_block.api.models.TrainingBlockDomain
import com.yankin.training_block.api.usecases.GetTrainingBlockByIdStreamUseCase
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetTrainingBlockByIdStreamUseCaseImpl @Inject constructor(
    private val trainingBlockRepository: TrainingBlockRepository,
    private val exerciseRepository: ExerciseRepository,
) : GetTrainingBlockByIdStreamUseCase {

    override fun invoke(trainingBlockId: Long): Flow<TrainingBlockDomain> {
        return trainingBlockRepository.getTrainingBlockByIdStream(trainingBlockId)
            .flatMapLatest { trainingBlock ->
                exerciseRepository.getExercisesByTrainingBlockIdStream(trainingBlock.id)
                    .map { exerciseDomains ->
                        when {
                            exerciseDomains.size == 1 -> TrainingBlockDomain.SingleExercise(
                                id = trainingBlock.id,
                                trainingId = trainingBlock.trainingId,
                                position = trainingBlock.position,
                                exercise = exerciseDomains.first()
                            )
                            exerciseDomains.size > 1 -> TrainingBlockDomain.SuperSet(
                                id = trainingBlock.id,
                                trainingId = trainingBlock.trainingId,
                                position = trainingBlock.position,
                                exerciseList = exerciseDomains
                            )
                            else -> throw NotValidTrainingBlockDomain()
                        }
                    }
            }
    }
}