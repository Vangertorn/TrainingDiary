package com.yankin.muscle_groups.impl.domain.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupListUseCase
import com.yankin.muscle_groups.impl.domain.repositories.MuscleGroupRepository
import javax.inject.Inject

internal class GetAllMuscleGroupListUseCaseImpl @Inject constructor(
    private val muscleGroupRepository: MuscleGroupRepository,
) : GetAllMuscleGroupListUseCase {
    override suspend fun invoke(): List<MuscleGroupDomain> {
        return muscleGroupRepository.getMuscleGroups()
    }
}