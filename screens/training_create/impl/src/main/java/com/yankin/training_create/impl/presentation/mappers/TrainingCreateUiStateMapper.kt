package com.yankin.training_create.impl.presentation.mappers

import com.yankin.date.DateFormatter
import com.yankin.training_create.impl.presentation.models.TrainingCreateStateModel
import com.yankin.training_create.impl.presentation.models.TrainingCreateUiState

internal fun TrainingCreateStateModel.toTrainingCreateUiState(): TrainingCreateUiState {

    return TrainingCreateUiState(
        muscleGroupList = muscleGroupList.map { muscleGroup ->
            muscleGroup.toMuscleGroupUiModel(selectedMuscleGroupIdList.contains(muscleGroup.id))
        },
        weight = weight?.toString() ?: "",
        comment = comment,
        selectedDate = DateFormatter.timestampToDate(
            timestamp = selectedDate,
        ),
    )
}