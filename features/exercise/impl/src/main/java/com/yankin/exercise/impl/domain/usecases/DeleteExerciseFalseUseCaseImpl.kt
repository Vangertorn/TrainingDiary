package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.usecases.DeleteExerciseFalseUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class DeleteExerciseFalseUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : DeleteExerciseFalseUseCase {

    override suspend fun invoke(exerciseId: Long) {
        exerciseRepository.deletedExerciseFalse(exerciseId)
    }
}