package com.yankin.exercise_pattern.api.usecases

interface UpdateExercisePatternByNameUseCase {

    suspend fun invoke(oldName: String, newName: String)
}