package com.yankin.membership.impl.presentation.membership.models

internal sealed interface MembershipCreateEvent {

    object Dismiss : MembershipCreateEvent

    object EndDateError : MembershipCreateEvent

    object NotEnoughTrainingCount : MembershipCreateEvent

    object Default : MembershipCreateEvent
}