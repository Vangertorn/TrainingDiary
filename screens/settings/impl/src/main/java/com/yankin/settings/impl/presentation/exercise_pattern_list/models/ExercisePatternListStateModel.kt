package com.yankin.settings.impl.presentation.exercise_pattern_list.models

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain

internal data class ExercisePatternListStateModel(
    val exercisePatternList: List<ExercisePatternDomain>,
)