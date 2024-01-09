package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import javax.inject.Inject

internal class GetExerciseListBySuperSetIdUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : GetExerciseListBySuperSetIdUseCase {

    override suspend fun invoke(superSetId: Long): List<ExerciseDomain> {
        return exerciseRepository.getExerciseListBySuperSetId(superSetId)
    }
}