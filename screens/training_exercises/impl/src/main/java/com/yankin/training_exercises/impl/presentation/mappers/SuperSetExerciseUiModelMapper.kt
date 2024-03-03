package com.yankin.training_exercises.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.training_exercises.impl.domain.TrainingExerciseModel
import com.yankin.training_exercises.impl.presentation.adapters.superset_exercises.SuperSetExerciseUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SingleExerciseUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SuperSetUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId

internal fun TrainingExerciseModel.toSuperSetExerciseUiModel(
    resourceManager: ResourceManager
): SuperSetExerciseUiModel {

    return SuperSetExerciseUiModel(
        exerciseName = SuperSetExerciseUiModel.Payload.ExerciseName(value = exercise.name),
        exerciseComment = SuperSetExerciseUiModel.Payload.ExerciseComment(value = exercise.comment ?: ""),
        exerciseId = exercise.id,
        exerciseSets = SuperSetExerciseUiModel.Payload.ExerciseSets(
            value = setList.map { setDomain ->
                setDomain.toExerciseSetUiModel(resourceManager)
            }
        )
    )
}