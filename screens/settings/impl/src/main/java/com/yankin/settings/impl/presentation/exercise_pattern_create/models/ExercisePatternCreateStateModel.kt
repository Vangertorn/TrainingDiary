package com.yankin.settings.impl.presentation.exercise_pattern_create.models

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain

internal data class ExercisePatternCreateStateModel(
    val exercisePattern: ExercisePatternDomain?,
    val exerciseNameByUser: String,
)