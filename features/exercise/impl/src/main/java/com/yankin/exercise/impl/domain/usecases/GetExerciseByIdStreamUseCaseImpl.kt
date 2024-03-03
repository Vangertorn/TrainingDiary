package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.GetExerciseByIdStreamUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetExerciseByIdStreamUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : GetExerciseByIdStreamUseCase {

    override fun invoke(exerciseId: Long): Flow<ExerciseDomain> {
        return exerciseRepository.getExerciseByIdStream(exerciseId)
    }
}