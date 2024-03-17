package com.yankin.workout_routines.impl.presentation.models

import com.yankin.approach_create.api.navigation.SetCreateParams

internal sealed interface WorkoutRoutinesEvent {

    object Default : WorkoutRoutinesEvent

    data class ShowSnackBar(
        val actionName: String,
        val message: String,
        val trainingBlockId: Long
    ) : WorkoutRoutinesEvent

    data class NavigateToSetCreate(
        val params: SetCreateParams
    ) : WorkoutRoutinesEvent
}