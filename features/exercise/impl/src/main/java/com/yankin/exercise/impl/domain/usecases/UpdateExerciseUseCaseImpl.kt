package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.usecases.UpdateExerciseUseCase
import com.yankin.exercise.api.repositories.ExerciseRepository
import javax.inject.Inject

internal class UpdateExerciseUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : UpdateExerciseUseCase {
    override suspend fun invoke(exerciseId: Long, exerciseName: String, exerciseComment: String?) {
        exerciseRepository.updateExercise(
            exerciseId = exerciseId, exerciseName = exerciseName, exerciseComment = exerciseComment
        )
    }
}