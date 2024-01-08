package com.yankin.exercise.api.usecases

interface DeleteExerciseFalseUseCase {

    suspend fun invoke(exerciseId: Long)
}