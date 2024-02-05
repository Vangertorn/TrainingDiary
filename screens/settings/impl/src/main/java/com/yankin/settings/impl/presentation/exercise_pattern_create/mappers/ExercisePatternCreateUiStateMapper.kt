package com.yankin.settings.impl.presentation.exercise_pattern_create.mappers

import com.yankin.settings.impl.presentation.exercise_pattern_create.models.ExercisePatternCreateStateModel
import com.yankin.settings.impl.presentation.exercise_pattern_create.models.ExercisePatternCreateUiState

internal fun ExercisePatternCreateStateModel.toExercisePatternCreateUiState(): ExercisePatternCreateUiState {

    return ExercisePatternCreateUiState(
        exercisePatternName = exerciseNameByUser,
    )
}