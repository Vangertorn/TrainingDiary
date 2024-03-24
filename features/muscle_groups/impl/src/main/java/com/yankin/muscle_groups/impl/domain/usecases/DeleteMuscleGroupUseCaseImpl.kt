package com.yankin.muscle_groups.impl.domain.usecases

import com.yankin.muscle_groups.api.usecases.DeleteMuscleGroupUseCase
import com.yankin.muscle_groups.api.repositories.MuscleGroupRepository
import javax.inject.Inject

internal class DeleteMuscleGroupUseCaseImpl @Inject constructor(
    private val muscleGroupRepository: MuscleGroupRepository,
) : DeleteMuscleGroupUseCase {
    override suspend fun invoke(muscleGroupId: Long) {
        muscleGroupRepository.deleteMuscleGroup(muscleGroupId)
    }
}