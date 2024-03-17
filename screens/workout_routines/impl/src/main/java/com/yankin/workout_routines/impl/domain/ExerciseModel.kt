package com.yankin.workout_routines.impl.domain

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.set.api.models.SetDomain

internal data class ExerciseModel(
    val exercise: ExerciseDomain,
    val setList: List<SetDomain>
)