package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.usecases.DeleteEmptyExerciseUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class DeleteEmptyExerciseUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : DeleteEmptyExerciseUseCase {
    override suspend fun invoke() {
        exerciseRepository.deleteEmptyExercise()
    }
}