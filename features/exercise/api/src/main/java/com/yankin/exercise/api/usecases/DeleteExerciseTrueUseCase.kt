package com.yankin.exercise.api.usecases

interface DeleteExerciseTrueUseCase {

    suspend fun invoke(exerciseId: Long)
}