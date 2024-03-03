package com.yankin.approach_create.impl.presentation.mappers

import com.yankin.approach_create.impl.presentation.adapters.sets.SetUiModel
import com.yankin.common.resource_import.CommonRString
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.set.api.models.SetDomain

internal fun SetDomain.toSetUiModel(
   resourceManager: ResourceManager,
): SetUiModel {

    return SetUiModel(
        setId = id,
        description = resourceManager.getString(CommonRString.set, weight.toString(), reps.toString())
    )
}