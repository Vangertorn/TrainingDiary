package com.yankin.workout_routines.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.workout_routines.impl.domain.ExerciseModel
import com.yankin.workout_routines.impl.presentation.adapters.superset_exercises.SuperSetExerciseUiModel

internal fun ExerciseModel.toSuperSetExerciseUiModel(
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