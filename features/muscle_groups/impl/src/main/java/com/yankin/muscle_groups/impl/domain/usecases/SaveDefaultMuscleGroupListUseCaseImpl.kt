package com.yankin.muscle_groups.impl.domain.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.usecases.SaveDefaultMuscleGroupListUseCase
import com.yankin.muscle_groups.api.repositories.MuscleGroupRepository
import javax.inject.Inject

internal class SaveDefaultMuscleGroupListUseCaseImpl @Inject constructor(
    private val muscleGroupRepository: MuscleGroupRepository,
) : SaveDefaultMuscleGroupListUseCase {
    override suspend fun invoke(defaultMuscleGroupList: List<MuscleGroupDomain>) {
        muscleGroupRepository.saveDefaultValues(defaultMuscleGroupList)
    }
}