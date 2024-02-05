package com.yankin.settings.impl.presentation.exercise_pattern_list.models

import com.yankin.settings.impl.navigation.ExercisePatternCreateDialogParams

internal sealed interface ExercisePatternListEvent {

    object Default : ExercisePatternListEvent

    data class NavigateToCreateExercisePattern(val params: ExercisePatternCreateDialogParams) : ExercisePatternListEvent
}