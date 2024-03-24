package com.yankin.muscle_groups.impl.domain.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.usecases.SaveMuscleGroupUseCase
import com.yankin.muscle_groups.api.repositories.MuscleGroupRepository
import javax.inject.Inject

internal class SaveMuscleGroupUseCaseImpl @Inject constructor(
    private val muscleGroupRepository: MuscleGroupRepository,
) : SaveMuscleGroupUseCase {
    override suspend fun invoke(muscleGroupDomain: MuscleGroupDomain) {
        muscleGroupRepository.saveMuscleGroup(muscleGroupDomain)
    }
}