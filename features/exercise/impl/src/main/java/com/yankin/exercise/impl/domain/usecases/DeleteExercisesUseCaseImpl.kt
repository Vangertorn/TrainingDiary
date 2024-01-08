package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.usecases.DeleteExercisesUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class DeleteExercisesUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : DeleteExercisesUseCase {

    override suspend fun invoke() {
        exerciseRepository.deleteExercises()
    }
}