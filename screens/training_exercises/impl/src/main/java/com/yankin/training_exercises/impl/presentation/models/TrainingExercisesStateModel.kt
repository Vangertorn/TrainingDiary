package com.yankin.training_exercises.impl.presentation.models

import com.yankin.training_exercises.impl.domain.TrainingExerciseModel
import com.yankin.training_exercises.impl.domain.TrainingSuperSetModel
import com.yankin.training.api.models.TrainingDomain

internal data class TrainingExercisesStateModel(
    val trainingId: Long,
    val trainingDomain: TrainingDomain?,
    val exerciseList: List<TrainingExerciseModel>,
    val supersetList: List<TrainingSuperSetModel>,
)