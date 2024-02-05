package com.yankin.exercise_pattern.api.usecases

import kotlinx.coroutines.flow.Flow

interface GetCurrentExercisePatternAsStringStreamUseCase {

    fun invoke(): Flow<List<String>>
}