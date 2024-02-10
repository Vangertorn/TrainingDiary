package com.yankin.settings.impl.presentation.settings.models

internal sealed interface SettingsEvent {

    object Default : SettingsEvent

    data class ShowInfoToast(val info: String) : SettingsEvent

    object NavigateToInformation : SettingsEvent

    data class RecoverMuscleGroupsDialog(
        val title: String,
        val message: String,
        val positiveButton: String,
        val negativeButton: String,
    ) : SettingsEvent

    object NavigateToExercisePattern : SettingsEvent
}