package com.yankin.exercise_name.impl.domain.usecases

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameStreamUseCase
import com.yankin.exercise_name.impl.domain.repositories.ExerciseNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentExerciseNameStreamUseCaseImpl @Inject constructor(
    private val exerciseNameRepository: ExerciseNameRepository,
) : GetCurrentExerciseNameStreamUseCase {
    override fun invoke(): Flow<List<ExerciseNameDomain>> {
        return exerciseNameRepository.currentExerciseNameStream
    }
}