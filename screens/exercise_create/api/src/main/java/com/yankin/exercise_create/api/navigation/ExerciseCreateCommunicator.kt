package com.yankin.exercise_create.api.navigation

interface ExerciseCreateCommunicator {

    fun navigateToExerciseCreate(params: ExerciseCreateParams)

    fun navigateToSuperSetCreate(params: SuperSetCreateParams)

    companion object {
        const val NAV_KEY = "ExerciseCreateCommunicatorNavKey"
    }
}