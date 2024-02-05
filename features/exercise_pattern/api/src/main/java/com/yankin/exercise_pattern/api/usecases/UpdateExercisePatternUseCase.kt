package com.yankin.exercise_pattern.api.usecases

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain

interface UpdateExercisePatternUseCase {

    suspend fun invoke(exercisePattern: ExercisePatternDomain)
}