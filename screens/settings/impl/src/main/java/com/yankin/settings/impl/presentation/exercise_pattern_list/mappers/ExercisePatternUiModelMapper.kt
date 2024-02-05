package com.yankin.settings.impl.presentation.exercise_pattern_list.mappers

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.settings.impl.presentation.exercise_pattern_list.adapter.ExercisePatternUiModel

internal fun ExercisePatternDomain.toExercisePatternUiModel(): ExercisePatternUiModel {
    return ExercisePatternUiModel(
        description = ExercisePatternUiModel.Payload.Description(name),
        exercisePatternId = id,
    )
}