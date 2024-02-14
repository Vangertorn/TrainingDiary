package com.yankin.exercise_create.impl.presentation.exercise_create.mappers

import com.yankin.common.resource_import.CommonRColor
import com.yankin.common.resource_import.CommonRDrawable
import com.yankin.exercise_create.impl.presentation.exercise_create.adapter.models.ExerciseUiModel
import com.yankin.exercise_create.impl.presentation.exercise_create.models.ExerciseTemporary

internal fun ExerciseTemporary.toExerciseUiModel(
    selectedExerciseId: Int?
): ExerciseUiModel {

    return ExerciseUiModel(
        exerciseName = ExerciseUiModel.Payload.ExerciseName(
            value = name
        ), exerciseId = id,
        background = ExerciseUiModel.Payload.Background(
            value = if (selectedExerciseId == id) {
                CommonRDrawable.background_item_muscle_groups_selected
            } else {
                CommonRDrawable.backgound_item_muscle_groups
            }
        ), textColor = ExerciseUiModel.Payload.TextColor(
            value = if (selectedExerciseId == id) {
                CommonRColor.white
            } else {
                CommonRColor.black
            }
        )
    )
}