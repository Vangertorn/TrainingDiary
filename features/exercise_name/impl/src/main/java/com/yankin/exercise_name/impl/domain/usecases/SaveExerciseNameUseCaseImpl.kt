package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExerciseNameRepository
import javax.inject.Inject

internal class SaveExerciseNameUseCaseImpl @Inject constructor(
    private val exerciseNameRepository: ExerciseNameRepository,
) : SaveExerciseNameUseCase {
    override suspend fun invoke(exerciseName: ExerciseNameDomain) {
        exerciseNameRepository.saveExerciseName(exerciseName)
    }
}