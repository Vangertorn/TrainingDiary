package com.yankin.muscle_groups.api.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain

interface DeleteMuscleGroupUseCase {
    suspend fun invoke(muscleGroupDomain: MuscleGroupDomain)
}