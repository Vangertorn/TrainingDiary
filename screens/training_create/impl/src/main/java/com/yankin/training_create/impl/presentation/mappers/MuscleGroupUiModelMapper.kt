package com.yankin.training_create.impl.presentation.mappers

import com.yankin.common.resource_import.CommonRColor
import com.yankin.common.resource_import.CommonRDrawable
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.training_create.impl.presentation.adapter.MuscleGroupUiModel

internal fun MuscleGroupDomain.toMuscleGroupUiModel(isSelect: Boolean): MuscleGroupUiModel {
    return MuscleGroupUiModel(
        description = nameMuscleGroup,
        muscleGroupId = id,
        background = MuscleGroupUiModel.Payload.Background(
            value = if (isSelect) {
                CommonRDrawable.background_item_muscle_groups_selected
            } else {
                CommonRDrawable.backgound_item_muscle_groups
            }
        ),
        textColor = MuscleGroupUiModel.Payload.TextColor(
            value = if (isSelect) {
                CommonRColor.background_dark
            } else {
                CommonRColor.white
            },
        )
    )
}