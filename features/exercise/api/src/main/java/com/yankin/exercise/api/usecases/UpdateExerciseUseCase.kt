package com.yankin.exercise.api.usecases

interface UpdateExerciseUseCase {
    suspend fun invoke(exerciseId: Long, exerciseName: String, exerciseComment: String?)
}