package com.yankin.workout_routines.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.workout_routines.impl.domain.TrainingBlockModel
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesStateModel
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesUiState

internal fun WorkoutRoutinesStateModel.toWorkoutRoutinesUiState(
    resourceManager: ResourceManager
): WorkoutRoutinesUiState {

    return WorkoutRoutinesUiState(
        trainingDate = trainingDomain?.date ?: "",
        trainingComment = trainingDomain?.comment ?: "",
        exercises = trainingBlockList.map { trainingBlockModel ->
            when (trainingBlockModel) {
                is TrainingBlockModel.SingleExercise -> trainingBlockModel.toSingleExerciseUiModel(resourceManager)
                is TrainingBlockModel.SuperSet -> trainingBlockModel.toSuperSetUiModel(resourceManager)
            }
        },
        trainingMuscleGroups = trainingDomain?.muscleGroups ?: "",
        trainingWeight = trainingDomain?.weight ?: "",
    )
}