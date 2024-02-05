package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.exercise_pattern.api.usecases.SaveExercisePatternUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import javax.inject.Inject

internal class SaveExercisePatternUseCaseImpl @Inject constructor(
    private val exercisePatternRepository: ExercisePatternRepository,
) : SaveExercisePatternUseCase {
    override suspend fun invoke(exercisePattern: ExercisePatternDomain) {
        exercisePatternRepository.saveExercisePattern(exercisePattern)
    }
}