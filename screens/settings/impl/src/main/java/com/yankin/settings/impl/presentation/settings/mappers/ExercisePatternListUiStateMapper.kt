package com.yankin.settings.impl.presentation.settings.mappers

import com.yankin.common.resource_import.CommonRString
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.settings.impl.presentation.settings.models.SettingsStateModel
import com.yankin.settings.impl.presentation.settings.models.SettingsUiState

internal fun SettingsStateModel.toExercisePatternListUiState(resourceManager: ResourceManager): SettingsUiState {

    return SettingsUiState(
        reps = reps.toString(),
        weight = weight.toString(),
        isLastTrainingTop = isLastTrainingTop,
        muscleGroupList = muscleGroupList.map { muscleGroup ->
            muscleGroup.toMuscleGroupUiModel()
        },
        muscleGroupNameByUser = muscleGroupNameByUser,
        trainingPositionDescription = if (isLastTrainingTop) {
            resourceManager.getString(CommonRString.Last_training_above)
        } else {
            resourceManager.getString(CommonRString.Last_training_below)
        }
    )
}