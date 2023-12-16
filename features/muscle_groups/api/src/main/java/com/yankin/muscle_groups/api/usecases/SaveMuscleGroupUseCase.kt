package com.yankin.muscle_groups.api.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain

interface SaveMuscleGroupUseCase {
    suspend fun invoke(muscleGroupDomain: MuscleGroupDomain)
}