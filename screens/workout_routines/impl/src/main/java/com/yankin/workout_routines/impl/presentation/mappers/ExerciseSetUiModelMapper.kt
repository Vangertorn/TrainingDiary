package com.yankin.workout_routines.impl.presentation.mappers

import com.yankin.common.resource_import.CommonRString
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.set.api.models.SetDomain
import com.yankin.workout_routines.impl.presentation.adapters.exercise_sets.ExerciseSetUiModel

internal fun SetDomain.toExerciseSetUiModel(
    resourceManager: ResourceManager
): ExerciseSetUiModel {

    return ExerciseSetUiModel(
        description = resourceManager.getString(CommonRString.set, weight.toString(), reps.toString())
    )
}