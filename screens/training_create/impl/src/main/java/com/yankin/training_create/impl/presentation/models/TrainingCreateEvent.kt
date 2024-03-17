package com.yankin.training_create.impl.presentation.models

import com.yankin.workout_routines.api.navigation.WorkoutRoutinesParams

internal sealed interface TrainingCreateEvent {

    object Back : TrainingCreateEvent

    object Default : TrainingCreateEvent

    data class NavigateToExerciseList(val params: WorkoutRoutinesParams) : TrainingCreateEvent
}