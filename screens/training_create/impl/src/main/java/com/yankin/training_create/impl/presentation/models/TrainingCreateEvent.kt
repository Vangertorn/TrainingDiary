package com.yankin.training_create.impl.presentation.models

import com.yankin.exercise_list.api.navigation.ExerciseListParams

internal sealed interface TrainingCreateEvent {

    object Back : TrainingCreateEvent

    object Default : TrainingCreateEvent

    data class NavigateToExerciseList(val params: ExerciseListParams) : TrainingCreateEvent
}