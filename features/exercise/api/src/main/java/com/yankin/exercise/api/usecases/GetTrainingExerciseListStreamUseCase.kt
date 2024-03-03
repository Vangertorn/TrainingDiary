package com.yankin.exercise.api.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import kotlinx.coroutines.flow.Flow

interface GetTrainingExerciseListStreamUseCase {

    fun invoke(trainingId: Long): Flow<List<ExerciseDomain>>
}