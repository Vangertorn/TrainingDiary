package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.exercise_pattern.api.usecases.UpdateExercisePatternUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import javax.inject.Inject

internal class UpdateExercisePatternUseCaseImpl @Inject constructor(
    private val exercisePatternRepository: ExercisePatternRepository,
) : UpdateExercisePatternUseCase {
    override suspend fun invoke(exercisePattern: ExercisePatternDomain) {
        exercisePatternRepository.updateExercisePattern(exercisePattern)
    }
}