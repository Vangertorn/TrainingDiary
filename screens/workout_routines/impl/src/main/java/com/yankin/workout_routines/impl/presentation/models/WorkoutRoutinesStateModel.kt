package com.yankin.workout_routines.impl.presentation.models

import com.yankin.workout_routines.impl.domain.TrainingBlockModel
import com.yankin.training.api.models.TrainingDomain

internal data class WorkoutRoutinesStateModel(
    val trainingId: Long,
    val trainingDomain: TrainingDomain?,
    val trainingBlockList: List<TrainingBlockModel>,
)