package com.yankin.training_exercises.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.training_exercises.impl.domain.TrainingSuperSetModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SuperSetUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId

internal fun TrainingSuperSetModel.toSuperSetUiModel(
    resourceManager: ResourceManager
): SuperSetUiModel {

    return SuperSetUiModel(
        id = TrainingExerciseId.SuperSetId(value = superSet.id),
        superSetExercises = SuperSetUiModel.Payload.SuperSetExercises(
            value = exerciseList.map { trainingExerciseModel ->
                trainingExerciseModel.toSuperSetExerciseUiModel(resourceManager)
            }
        )
    )
}