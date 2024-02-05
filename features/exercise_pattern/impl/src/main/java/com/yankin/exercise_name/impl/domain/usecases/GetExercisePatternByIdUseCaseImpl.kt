package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.exercise_pattern.api.usecases.GetExercisePatternByIdUseCase
import javax.inject.Inject

internal class GetExercisePatternByIdUseCaseImpl @Inject constructor(
    private val exercisePatternRepository: ExercisePatternRepository,
) : GetExercisePatternByIdUseCase {
    override suspend fun invoke(exercisePatternId: Long): ExercisePatternDomain {
        return exercisePatternRepository.getExercisePatternById(exercisePatternId)
    }
}