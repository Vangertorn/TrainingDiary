package com.yankin.exercise_create.impl.presentation.models

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain

internal data class ExerciseCreateStateModel(
    val exerciseName: String,
    val exerciseComment: String?,
    val exercisePatternList: List<ExercisePatternDomain>,
    val exerciseList: List<ExerciseTemporary>,
    val selectedExerciseId: Int?,
)