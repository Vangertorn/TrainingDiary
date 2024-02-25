package com.yankin.approach_create.impl.presentation.mappers

import com.yankin.approach_create.impl.presentation.models.SetCreateStateModel
import com.yankin.approach_create.impl.presentation.models.SetCreateUiState
import com.yankin.resource_manager.api.ResourceManager

internal fun SetCreateStateModel.toExerciseCreateUiState(
    resourceManager: ResourceManager
): SetCreateUiState {

    return SetCreateUiState(
        exerciseName = exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == selectedExerciseId
        }?.let { selectedExerciseStateModel ->
            selectedExerciseStateModel.exerciseNameByUser ?: selectedExerciseStateModel.exerciseName
        } ?: "",
        exerciseComment = exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == selectedExerciseId
        }?.let { selectedExerciseStateModel ->
            selectedExerciseStateModel.exerciseCommentByUser ?: selectedExerciseStateModel.exerciseComment
        } ?: "",
        exerciseList = if (exerciseStateModelList.size > 1) {
            exerciseStateModelList.map { exerciseStateModel ->
                exerciseStateModel.toExerciseUiModel(selectedExerciseId)
            }
        } else {
            emptyList()
        },
        autoCompleteExercise = exercisePatternList.map { exercisePattern ->
            exercisePattern.name
        },
        setList = exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == selectedExerciseId
        }?.let { selectedExerciseStateModel ->
            selectedExerciseStateModel.setList.map { setDomain ->
                setDomain.toSetUiModel(resourceManager)
            }
        } ?: emptyList(),
        weight = (exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == selectedExerciseId
        }?.let { selectedExerciseStateModel ->
            selectedExerciseStateModel.weightByUser ?: selectedExerciseStateModel.weightCurrentSet
        } ?: defaultWeight).toString(),
        reps = (exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == selectedExerciseId
        }?.let { selectedExerciseStateModel ->
            selectedExerciseStateModel.repsByUser ?: selectedExerciseStateModel.repsCurrentSet
        } ?: defaultReps).toString(),
    )
}