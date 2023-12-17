package com.yankin.exercese_name.api.usecases

import com.yankin.exercese_name.api.models.ExerciseNameDomain

interface SaveExerciseNameUseCase {

    suspend fun invoke(exerciseName: ExerciseNameDomain)
}