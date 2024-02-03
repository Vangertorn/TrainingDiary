package com.yankin.training_list.impl.presentation.models

internal sealed interface TrainingListEvent {

    object Default : TrainingListEvent

    object NavigateToCreateMembership : TrainingListEvent

    data class NavigateToEditMembership(val membershipId: Long) : TrainingListEvent
}