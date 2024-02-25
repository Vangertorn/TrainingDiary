package com.yankin.exercise_pattern.api.usecases

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import kotlinx.coroutines.flow.Flow

interface GetCurrentExercisePatternStreamUseCase {

    fun invoke(): Flow<List<ExercisePatternDomain>>
}