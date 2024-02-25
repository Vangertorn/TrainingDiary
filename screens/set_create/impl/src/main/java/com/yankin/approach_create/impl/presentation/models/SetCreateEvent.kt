package com.yankin.approach_create.impl.presentation.models

internal sealed interface SetCreateEvent {

    object Default : SetCreateEvent

    data class ShowToast(val info: String) : SetCreateEvent
}