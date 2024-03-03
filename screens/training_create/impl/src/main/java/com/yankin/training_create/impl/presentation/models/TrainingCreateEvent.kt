package com.yankin.training_create.impl.presentation.models

import com.yankin.training_exercises.api.navigation.TrainingExercisesParams

internal sealed interface TrainingCreateEvent {

    object Back : TrainingCreateEvent

    object Default : TrainingCreateEvent

    data class NavigateToExerciseList(val params: TrainingExercisesParams) : TrainingCreateEvent
}