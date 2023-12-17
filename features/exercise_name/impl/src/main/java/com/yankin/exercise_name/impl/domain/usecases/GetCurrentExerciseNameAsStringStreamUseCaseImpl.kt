package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameAsStringStreamUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExerciseNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentExerciseNameAsStringStreamUseCaseImpl @Inject constructor(
    private val exerciseNameRepository: ExerciseNameRepository,
) : GetCurrentExerciseNameAsStringStreamUseCase {
    override fun invoke(): Flow<List<String>> {
        return exerciseNameRepository.currentExerciseNameAsStringStream
    }
}