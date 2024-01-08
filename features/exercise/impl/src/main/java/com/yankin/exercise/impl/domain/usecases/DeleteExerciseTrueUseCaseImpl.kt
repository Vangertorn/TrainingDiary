package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.usecases.DeleteExerciseTrueUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class DeleteExerciseTrueUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : DeleteExerciseTrueUseCase {

    override suspend fun invoke(exerciseId: Long) {
        exerciseRepository.deletedExerciseTrue(exerciseId)
    }
}