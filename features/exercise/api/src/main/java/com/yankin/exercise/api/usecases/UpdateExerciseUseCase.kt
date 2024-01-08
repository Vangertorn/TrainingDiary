package com.yankin.exercise.api.usecases

import com.yankin.exercise.api.models.ExerciseDomain

interface UpdateExerciseUseCase {
    suspend fun invoke(exercise: ExerciseDomain)
}