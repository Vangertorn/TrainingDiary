package com.yankin.workout_routines.impl.presentation.mappers

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.workout_routines.impl.domain.TrainingBlockModel
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.SingleExerciseUiModel
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingBlockId

internal fun TrainingBlockModel.SingleExercise.toSingleExerciseUiModel(
    resourceManager: ResourceManager
): SingleExerciseUiModel {

    return SingleExerciseUiModel(
        exerciseName = SingleExerciseUiModel.Payload.ExerciseName(value = exercise.exercise.name),
        exerciseComment = SingleExerciseUiModel.Payload.ExerciseComment(value = exercise.exercise.comment ?: ""),
        id = TrainingBlockId.SingleBlockId(value = trainingBlockDomain.id),
        exerciseSets = SingleExerciseUiModel.Payload.ExerciseSets(
            value = exercise.setList.map { setDomain ->
                setDomain.toExerciseSetUiModel(resourceManager)
            }
        )
    )
}