package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdStreamUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetExerciseListBySuperSetIdStreamUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : GetExerciseListBySuperSetIdStreamUseCase {

    override fun invoke(superSetId: Long): Flow<List<ExerciseDomain>> {
        return exerciseRepository.getExerciseListBySuperSetIdStream(superSetId)
    }
}