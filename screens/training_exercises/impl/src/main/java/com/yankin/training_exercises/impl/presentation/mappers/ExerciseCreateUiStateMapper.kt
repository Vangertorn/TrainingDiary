package com.yankin.training_exercises.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.training_exercises.impl.presentation.models.TrainingExercisesStateModel
import com.yankin.training_exercises.impl.presentation.models.TrainingExercisesUiState

internal fun TrainingExercisesStateModel.toTrainingExercisesUiState(
    resourceManager: ResourceManager
): TrainingExercisesUiState {

    return TrainingExercisesUiState(
        trainingDate = trainingDomain?.date ?: "",
        trainingComment = trainingDomain?.comment ?: "",
        exercises = exerciseList.map { trainingExerciseModel ->
            trainingExerciseModel.toSingleExerciseUiModel(resourceManager)
        } + supersetList.map { trainingSuperSetModel ->
            trainingSuperSetModel.toSuperSetUiModel(resourceManager)
        },
        trainingMuscleGroups = trainingDomain?.muscleGroups ?: "",
        trainingWeight = trainingDomain?.weight ?: "",
    )
}