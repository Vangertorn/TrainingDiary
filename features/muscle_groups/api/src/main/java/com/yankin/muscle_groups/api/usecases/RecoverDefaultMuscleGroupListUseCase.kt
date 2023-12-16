package com.yankin.muscle_groups.api.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain

interface RecoverDefaultMuscleGroupListUseCase {
    suspend fun invoke(defaultMuscleGroupList: List<MuscleGroupDomain>)
}