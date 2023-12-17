package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import com.yankin.exercese_name.api.usecases.DeleteExerciseNameUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExerciseNameRepository
import javax.inject.Inject

internal class DeleteExerciseNameUseCaseImpl @Inject constructor(
    private val exerciseNameRepository: ExerciseNameRepository,
) : DeleteExerciseNameUseCase {
    override suspend fun invoke(exerciseName: ExerciseNameDomain) {
        exerciseNameRepository.deleteExerciseName(exerciseName)
    }
}