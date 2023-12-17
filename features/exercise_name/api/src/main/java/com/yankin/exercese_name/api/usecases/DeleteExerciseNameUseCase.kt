package com.yankin.exercese_name.api.usecases

import com.yankin.exercese_name.api.models.ExerciseNameDomain

interface DeleteExerciseNameUseCase {

    suspend fun invoke(exerciseName: ExerciseNameDomain)
}