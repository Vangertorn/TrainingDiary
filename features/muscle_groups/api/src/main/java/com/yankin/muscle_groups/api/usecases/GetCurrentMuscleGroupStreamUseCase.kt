package com.yankin.muscle_groups.api.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import kotlinx.coroutines.flow.Flow

interface GetCurrentMuscleGroupStreamUseCase {
    fun invoke(): Flow<List<MuscleGroupDomain>>
}