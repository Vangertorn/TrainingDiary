package com.yankin.exercise.api.usecases

interface SwitchExercisePositionUseCase {

    suspend fun invoke(
        firstExerciseId: Long,
        firstExercisePosition: Int,
        secondExerciseId: Long,
        secondExercisePosition: Int
    )
}