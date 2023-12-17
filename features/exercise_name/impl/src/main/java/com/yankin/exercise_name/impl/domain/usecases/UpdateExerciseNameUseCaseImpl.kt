package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import com.yankin.exercese_name.api.usecases.UpdateExerciseNameUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExerciseNameRepository
import javax.inject.Inject

internal class UpdateExerciseNameUseCaseImpl @Inject constructor(
    private val exerciseNameRepository: ExerciseNameRepository,
) : UpdateExerciseNameUseCase {
    override suspend fun invoke(exerciseName: ExerciseNameDomain) {
        exerciseNameRepository.updateExerciseName(exerciseName)
    }
}