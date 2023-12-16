package com.yankin.muscle_groups.api.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain

interface GetAllMuscleGroupListUseCase {
    suspend fun invoke(): List<MuscleGroupDomain>
}