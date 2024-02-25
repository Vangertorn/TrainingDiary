package com.yankin.approach_create.impl.presentation.mappers

import com.yankin.approach_create.impl.presentation.adapters.exercises.ExerciseUiModel
import com.yankin.approach_create.impl.presentation.state.ExerciseStateModel
import com.yankin.common.resource_import.CommonRColor
import com.yankin.common.resource_import.CommonRDrawable

internal fun ExerciseStateModel.toExerciseUiModel(
    selectedExerciseId: Long?
): ExerciseUiModel {

    return ExerciseUiModel(
        exerciseName = ExerciseUiModel.Payload.ExerciseName(
            value = exerciseName
        ),
        exerciseId = exerciseId,
        background = ExerciseUiModel.Payload.Background(
            value = if (selectedExerciseId == exerciseId) {
                CommonRDrawable.background_item_muscle_groups_selected
            } else {
                CommonRDrawable.backgound_item_muscle_groups
            }
        ), textColor = ExerciseUiModel.Payload.TextColor(
            value = if (selectedExerciseId == exerciseId) {
                CommonRColor.white
            } else {
                CommonRColor.black
            }
        )
    )
}