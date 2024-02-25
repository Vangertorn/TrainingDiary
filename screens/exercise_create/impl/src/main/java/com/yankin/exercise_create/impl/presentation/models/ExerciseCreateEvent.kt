package com.yankin.exercise_create.impl.presentation.models

internal sealed interface ExerciseCreateEvent {

    object Default : ExerciseCreateEvent

    data class ShowToast(val info: String) : ExerciseCreateEvent

    object Dismiss: ExerciseCreateEvent
}