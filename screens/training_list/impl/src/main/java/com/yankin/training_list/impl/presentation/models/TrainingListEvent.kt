package com.yankin.training_list.impl.presentation.models

import com.yankin.membership.api.navigation.MembershipParams
import com.yankin.workout_routines.api.navigation.WorkoutRoutinesParams

internal sealed interface TrainingListEvent {

    object Default : TrainingListEvent

    object NavigateToCreateMembership : TrainingListEvent

    data class NavigateToEditMembership(val params: MembershipParams) : TrainingListEvent

    data class NavigateToWorkout(val params: WorkoutRoutinesParams) : TrainingListEvent

    data class ShowSnackBar(
        val actionName: String,
        val message: String,
        val trainingId: Long
    ) : TrainingListEvent
}