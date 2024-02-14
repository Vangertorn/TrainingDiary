package com.yankin.exercise_create.impl.presentation.exercise_create.mappers

import com.yankin.common.resource_import.CommonRColor
import com.yankin.common.resource_import.CommonRDrawable
import com.yankin.common.resource_import.CommonRString
import com.yankin.exercise_create.impl.presentation.exercise_create.adapter.models.AddExerciseUiModel
import com.yankin.exercise_create.impl.presentation.exercise_create.models.ExerciseCreateStateModel
import com.yankin.exercise_create.impl.presentation.exercise_create.models.ExerciseCreateUiState
import com.yankin.resource_manager.api.ResourceManager

internal fun ExerciseCreateStateModel.toExerciseCreateUiState(
    resourceManager: ResourceManager
): ExerciseCreateUiState {

    return ExerciseCreateUiState(
        exerciseName = exerciseName,
        exerciseComment = exerciseComment ?: "",
        superSetList = if (exerciseList.isEmpty()) {
            emptyList()
        } else {
            buildList {
                add(getAddExerciseUiModel(resourceManager = resourceManager, selectedExerciseId = selectedExerciseId))
                exerciseList.forEach { exerciseTemporary ->
                    add(exerciseTemporary.toExerciseUiModel(selectedExerciseId))
                }

            }
        },
        actionButtonDescription = when {
            exerciseList.isNotEmpty() && selectedExerciseId != null ->
                resourceManager.getString(CommonRString.delete_from_set)

            else -> resourceManager.getString(CommonRString.add_to_set)
        },
        autoCompleteExercise = exercisePatternList.map { exercisePattern ->
            exercisePattern.name
        }
    )
}

private fun getAddExerciseUiModel(resourceManager: ResourceManager, selectedExerciseId: Int?): AddExerciseUiModel {
    return AddExerciseUiModel(
        description = AddExerciseUiModel.Payload.Description(
            value = resourceManager.getString(CommonRString.add_exercise)
        ), background = AddExerciseUiModel.Payload.Background(
            value = if (selectedExerciseId == null) {
                CommonRDrawable.background_item_muscle_groups_selected
            } else {
                CommonRDrawable.backgound_item_muscle_groups
            }
        ), textColor = AddExerciseUiModel.Payload.TextColor(
            value = if (selectedExerciseId == null) {
                CommonRColor.white
            } else {
                CommonRColor.black
            }
        )
    )
}