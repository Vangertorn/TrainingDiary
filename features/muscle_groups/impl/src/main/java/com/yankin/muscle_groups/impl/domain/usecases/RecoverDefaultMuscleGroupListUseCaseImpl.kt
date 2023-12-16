package com.yankin.muscle_groups.impl.domain.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.usecases.RecoverDefaultMuscleGroupListUseCase
import com.yankin.muscle_groups.impl.domain.repositories.MuscleGroupRepository
import javax.inject.Inject

internal class RecoverDefaultMuscleGroupListUseCaseImpl @Inject constructor(
    private val muscleGroupRepository: MuscleGroupRepository,
) : RecoverDefaultMuscleGroupListUseCase {
    override suspend fun invoke(defaultMuscleGroupList: List<MuscleGroupDomain>){
        muscleGroupRepository.recoverDefaultValues(defaultMuscleGroupList)
    }
}