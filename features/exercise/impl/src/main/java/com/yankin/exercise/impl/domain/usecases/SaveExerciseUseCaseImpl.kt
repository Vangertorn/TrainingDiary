package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.SaveExerciseUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class SaveExerciseUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : SaveExerciseUseCase {
    override suspend fun invoke(exercise: ExerciseDomain) {
        exerciseRepository.saveExercise(exercise)
    }
}