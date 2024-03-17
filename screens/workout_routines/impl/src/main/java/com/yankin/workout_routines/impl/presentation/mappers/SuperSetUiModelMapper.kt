package com.yankin.workout_routines.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.workout_routines.impl.domain.TrainingBlockModel
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.SuperSetUiModel
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingBlockId

internal fun TrainingBlockModel.SuperSet.toSuperSetUiModel(
    resourceManager: ResourceManager
): SuperSetUiModel {

    return SuperSetUiModel(
        id = TrainingBlockId.SuperSetId(value = trainingBlockDomain.id),
        superSetExercises = SuperSetUiModel.Payload.SuperSetExercises(
            value = exerciseList.map { trainingExerciseModel ->
                trainingExerciseModel.toSuperSetExerciseUiModel(resourceManager)
            }
        )
    )
}