package com.yankin.approach_create.impl.presentation.models

import com.yankin.approach_create.impl.presentation.state.ExerciseStateModel
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain

internal data class SetCreateStateModel(
    val exercisePatternList: List<ExercisePatternDomain>,
    val exerciseStateModelList: List<ExerciseStateModel>,
    val selectedExerciseId: Long?,
    val defaultWeight: Double,
    val defaultReps: Int,
)