package com.yankin.muscle_groups.api.usecases

interface DeleteMuscleGroupUseCase {
    suspend fun invoke(muscleGroupId: Long)
}