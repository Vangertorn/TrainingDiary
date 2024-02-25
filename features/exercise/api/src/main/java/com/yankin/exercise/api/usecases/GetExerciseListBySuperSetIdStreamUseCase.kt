package com.yankin.exercise.api.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import kotlinx.coroutines.flow.Flow

interface GetExerciseListBySuperSetIdStreamUseCase {

   fun invoke(superSetId: Long): Flow<List<ExerciseDomain>>
}