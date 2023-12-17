package com.yankin.exercese_name.api.usecases

import kotlinx.coroutines.flow.Flow

interface GetCurrentExerciseNameAsStringStreamUseCase {

    fun invoke(): Flow<List<String>>
}