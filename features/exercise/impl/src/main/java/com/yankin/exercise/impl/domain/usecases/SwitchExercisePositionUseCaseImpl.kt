package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.usecases.SwitchExercisePositionUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class SwitchExercisePositionUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : SwitchExercisePositionUseCase {

    override suspend fun invoke(
        firstExerciseId: Long,
        firstExercisePosition: Int,
        secondExerciseId: Long,
        secondExercisePosition: Int
    ) {
        exerciseRepository.switchExercisePosition(
            firstExerciseId = firstExerciseId,
            firstExercisePosition = firstExercisePosition,
            secondExerciseId = secondExerciseId,
            secondExercisePosition = secondExercisePosition,
        )
    }
}