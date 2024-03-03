package com.yankin.training_exercises.impl.domain

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.set.api.models.SetDomain

internal data class TrainingExerciseModel(
    val exercise: ExerciseDomain,
    val setList: List<SetDomain>
)