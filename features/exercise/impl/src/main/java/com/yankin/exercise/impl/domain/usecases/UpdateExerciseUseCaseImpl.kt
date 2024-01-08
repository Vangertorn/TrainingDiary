package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.UpdateExerciseUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class UpdateExerciseUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : UpdateExerciseUseCase {
    override suspend fun invoke(exercise: ExerciseDomain) {
        exerciseRepository.updateExercise(exercise)
    }
}