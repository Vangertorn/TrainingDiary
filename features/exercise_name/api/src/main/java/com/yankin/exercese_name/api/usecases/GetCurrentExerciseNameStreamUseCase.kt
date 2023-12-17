package com.yankin.exercese_name.api.usecases

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import kotlinx.coroutines.flow.Flow

interface GetCurrentExerciseNameStreamUseCase {

    fun invoke(): Flow<List<ExerciseNameDomain>>
}