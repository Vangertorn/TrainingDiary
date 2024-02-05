package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercise_pattern.api.usecases.DeleteExercisePatternUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import javax.inject.Inject

internal class DeleteExercisePatternUseCaseImpl @Inject constructor(
    private val exercisePatternRepository: ExercisePatternRepository,
) : DeleteExercisePatternUseCase {
    override suspend fun invoke(exercisePatternId: Long) {
        exercisePatternRepository.deleteExercisePattern(exercisePatternId)
    }
}