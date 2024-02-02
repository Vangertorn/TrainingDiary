package com.yankin.muscle_groups.impl.domain.usecases

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupStreamUseCase
import com.yankin.muscle_groups.impl.domain.repositories.MuscleGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetAllMuscleGroupStreamUseCaseImpl @Inject constructor(
    private val muscleGroupRepository: MuscleGroupRepository,
) : GetAllMuscleGroupStreamUseCase {
    override fun invoke(): Flow<List<MuscleGroupDomain>> {
        return muscleGroupRepository.currentMuscleGroupFlow
    }
}