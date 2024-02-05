package com.yankin.settings.impl.presentation.exercise_pattern_list.models

import com.yankin.settings.impl.presentation.exercise_pattern_list.adapter.ExercisePatternUiModel
import java.util.Date

internal data class ExercisePatternListUiState(
    val exercisePatternList: List<ExercisePatternUiModel>,
)