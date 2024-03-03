package com.yankin.training_exercises.impl.domain

import com.yankin.super_set.api.models.SuperSetDomain

internal data class TrainingSuperSetModel(
    val exerciseList: List<TrainingExerciseModel>,
    val superSet: SuperSetDomain,
)