package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.GetCurrentExerciseListStreamUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentExerciseListStreamUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : GetCurrentExerciseListStreamUseCase {

    override fun invoke(): Flow<List<ExerciseDomain>> {
        return exerciseRepository.currentExerciseListStream
    }
}