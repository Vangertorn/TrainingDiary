package com.yankin.settings.impl.presentation.exercise_pattern_list.mappers

import com.yankin.settings.impl.presentation.exercise_pattern_list.models.ExercisePatternListStateModel
import com.yankin.settings.impl.presentation.exercise_pattern_list.models.ExercisePatternListUiState

internal fun ExercisePatternListStateModel.toExercisePatternListUiState(): ExercisePatternListUiState {

    return ExercisePatternListUiState(
        exercisePatternList = exercisePatternList.map { exercisePattern ->
            exercisePattern.toExercisePatternUiModel()
        }
    )
}