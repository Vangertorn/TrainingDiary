package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import com.yankin.exercise_pattern.api.usecases.UpdateExercisePatternByNameUseCase
import javax.inject.Inject

internal class UpdateExercisePatternByNameUseCaseImpl @Inject constructor(
    private val exercisePatternRepository: ExercisePatternRepository,
) : UpdateExercisePatternByNameUseCase {
    override suspend fun invoke(oldName: String, newName: String) {
        exercisePatternRepository.updateExercisePatternByName(oldName = oldName, newName = newName)
    }
}