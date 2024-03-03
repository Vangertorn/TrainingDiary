package com.yankin.training_exercises.impl.presentation.models

import com.yankin.approach_create.api.navigation.SetCreateParams
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId

internal sealed interface TrainingExercisesEvent {

    object Default : TrainingExercisesEvent

    data class ShowSnackBar(
        val actionName: String,
        val message: String,
        val trainingExerciseId: TrainingExerciseId
    ) : TrainingExercisesEvent

    data class NavigateToSetCreate(
        val params: SetCreateParams
    ) : TrainingExercisesEvent
}