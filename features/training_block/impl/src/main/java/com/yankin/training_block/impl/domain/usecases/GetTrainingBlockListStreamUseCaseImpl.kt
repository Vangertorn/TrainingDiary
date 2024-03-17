package com.yankin.training_block.impl.domain.usecases

import com.yankin.exercise.api.repositories.ExerciseRepository
import com.yankin.training_block.api.NotValidTrainingBlockDomain
import com.yankin.training_block.api.models.TrainingBlockDomain
import com.yankin.training_block.api.usecases.GetTrainingBlockListStreamUseCase
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetTrainingBlockListStreamUseCaseImpl @Inject constructor(
    private val trainingBlockRepository: TrainingBlockRepository,
    private val exerciseRepository: ExerciseRepository,
) : GetTrainingBlockListStreamUseCase {

    override fun invoke(trainingId: Long): Flow<List<TrainingBlockDomain>> {
        return trainingBlockRepository.getTrainingBlockByTrainingIdStream(trainingId)
            .flatMapLatest { trainingBlockList ->
                combine(
                    trainingBlockList.map { trainingBlock ->
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
                ) { trainingBlockDomainArray ->
                    trainingBlockDomainArray.toList()
                }
            }
    }
}