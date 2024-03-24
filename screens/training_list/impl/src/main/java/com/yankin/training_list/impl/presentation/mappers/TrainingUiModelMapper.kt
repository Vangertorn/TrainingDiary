package com.yankin.training_list.impl.presentation.mappers

import com.yankin.common.resource_import.CommonRString
import com.yankin.date.DateFormatter
import com.yankin.date.DateFormatter.toDateStringOrEmpty
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.training.api.models.TrainingDomain
import com.yankin.training_list.impl.presentation.adapter.TrainingUiModel

internal fun TrainingDomain.toTrainingUiModel(
    resourceManager: ResourceManager
): TrainingUiModel {

    return TrainingUiModel(
        trainingDate = TrainingUiModel.Payload.TrainingDate(
            value = date.toDateStringOrEmpty(dateFormat = DateFormatter.DD_POINT_MM_POINT_YY)
        ),
        trainingComment = TrainingUiModel.Payload.TrainingComment(value = comment ?: ""),
        trainingId = id,
        muscleGroups = TrainingUiModel.Payload.MuscleGroups(value = selectedMuscleGroup.getMuscleGroupsString()),
        personWeight = TrainingUiModel.Payload.PersonWeight(
            value = personWeight?.let { weight ->
                resourceManager.getString(CommonRString.weight, weight.toString())
            } ?: "")
    )
}

private fun List<MuscleGroupDomain>.getMuscleGroupsString(): String {
    val stringBuilder = StringBuilder()
    forEach { muscleGroup ->
        stringBuilder.append(muscleGroup.nameMuscleGroup)
        stringBuilder.append(", ")
    }
    return stringBuilder.toString().removeSuffix(", ")
}