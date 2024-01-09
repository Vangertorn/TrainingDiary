package com.yankin.exercise.api.usecases

import com.yankin.exercise.api.models.ExerciseDomain

interface GetExerciseListBySuperSetIdUseCase {

    suspend fun invoke(superSetId: Long): List<ExerciseDomain>
}