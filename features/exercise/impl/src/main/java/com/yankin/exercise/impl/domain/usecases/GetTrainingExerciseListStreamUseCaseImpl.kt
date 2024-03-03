package com.yankin.exercise.impl.domain.usecases

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.GetTrainingExerciseListStreamUseCase
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetTrainingExerciseListStreamUseCaseImpl @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
) : GetTrainingExerciseListStreamUseCase {

    override fun invoke(trainingId: Long): Flow<List<ExerciseDomain>> {
        return exerciseRepository.getExercisesByTrainingIdStream(trainingId)
    }
}