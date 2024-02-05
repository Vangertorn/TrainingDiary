package com.yankin.exercise_pattern.api.usecases

interface DeleteExercisePatternUseCase {

    suspend fun invoke(exercisePatternId: Long)
}