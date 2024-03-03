package com.yankin.training_exercises.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.training_exercises.impl.domain.TrainingExerciseModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SingleExerciseUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId

internal fun TrainingExerciseModel.toSingleExerciseUiModel(
    resourceManager: ResourceManager
): SingleExerciseUiModel {

    return SingleExerciseUiModel(
        exerciseName = SingleExerciseUiModel.Payload.ExerciseName(value = exercise.name),
        exerciseComment = SingleExerciseUiModel.Payload.ExerciseComment(value = exercise.comment ?: ""),
        id = TrainingExerciseId.SingleExerciseId(value = exercise.id),
        exerciseSets = SingleExerciseUiModel.Payload.ExerciseSets(
            value = setList.map { setDomain ->
                setDomain.toExerciseSetUiModel(resourceManager)
            }
        )
    )
}