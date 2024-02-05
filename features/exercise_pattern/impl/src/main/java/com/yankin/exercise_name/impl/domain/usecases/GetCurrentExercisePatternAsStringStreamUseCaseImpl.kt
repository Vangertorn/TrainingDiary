package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternAsStringStreamUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentExercisePatternAsStringStreamUseCaseImpl @Inject constructor(
    private val exercisePatternRepository: ExercisePatternRepository,
) : GetCurrentExercisePatternAsStringStreamUseCase {
    override fun invoke(): Flow<List<String>> {
        return exercisePatternRepository.currentExercisePatternAsStringStream
    }
}