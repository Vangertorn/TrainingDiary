package com.yankin.training.impl.domain.usecases

import com.yankin.muscle_groups.api.repositories.MuscleGroupRepository
import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.GetCurrentTrainingAscStreamUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetCurrentTrainingAscStreamUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val muscleGroupRepository: MuscleGroupRepository,
) : GetCurrentTrainingAscStreamUseCase {
    override fun invoke(): Flow<List<TrainingDomain>> =
        trainingRepository.currentTrainingAscStream.map { trainingList ->
            trainingList.map { training ->
                TrainingDomain(
                    id = training.id,
                    date = training.date,
                    comment = training.comment,
                    personWeight = training.personWeight,
                    selectedMuscleGroup =  muscleGroupRepository.getMuscleGroupsByIds(training.selectedMuscleGroup)
                )
            }
        }
}