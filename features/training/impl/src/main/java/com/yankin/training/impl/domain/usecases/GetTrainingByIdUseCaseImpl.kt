package com.yankin.training.impl.domain.usecases

import com.yankin.muscle_groups.api.repositories.MuscleGroupRepository
import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.training.impl.domain.repositories.TrainingRepository
import javax.inject.Inject

internal class GetTrainingByIdUseCaseImpl @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val muscleGroupRepository: MuscleGroupRepository,
) : GetTrainingByIdUseCase {

    override suspend fun invoke(trainingId: Long): TrainingDomain {
        val training = trainingRepository.getTrainingById(trainingId)
        return TrainingDomain(
            id = training.id,
            date = training.date,
            comment = training.comment,
            personWeight = training.personWeight,
            selectedMuscleGroup = muscleGroupRepository.getMuscleGroupsByIds(training.selectedMuscleGroup)
        )
    }
}