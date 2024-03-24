package com.yankin.training.impl.domain.usecases

import com.yankin.muscle_groups.api.repositories.MuscleGroupRepository
import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.GetCurrentTrainingDescStreamUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetCurrentTrainingDescStreamUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val muscleGroupRepository: MuscleGroupRepository,
) : GetCurrentTrainingDescStreamUseCase {
    override fun invoke(): Flow<List<TrainingDomain>> =
        trainingRepository.currentTrainingDescStream.map { trainingList ->
            trainingList.map { training ->
                TrainingDomain(
                    id = training.id,
                    date = training.date,
                    comment = training.comment,
                    personWeight = training.personWeight,
                    selectedMuscleGroup = muscleGroupRepository.getMuscleGroupsByIds(training.selectedMuscleGroup)
                )
            }
        }
}