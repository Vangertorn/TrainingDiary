package com.yankin.training_list.impl.presentation.models

import com.yankin.membership.api.navigation.MembershipParams

internal sealed interface TrainingListEvent {

    object Default : TrainingListEvent

    object NavigateToCreateMembership : TrainingListEvent

    data class NavigateToEditMembership(val params: MembershipParams) : TrainingListEvent
}