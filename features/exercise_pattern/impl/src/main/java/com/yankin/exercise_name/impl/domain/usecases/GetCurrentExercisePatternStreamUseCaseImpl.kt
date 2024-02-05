package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternStreamUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentExercisePatternStreamUseCaseImpl @Inject constructor(
    private val exercisePatternRepository: ExercisePatternRepository,
) : GetCurrentExercisePatternStreamUseCase {
    override fun invoke(): Flow<List<ExercisePatternDomain>> {
        return exercisePatternRepository.currentExercisePatternStream
    }
}