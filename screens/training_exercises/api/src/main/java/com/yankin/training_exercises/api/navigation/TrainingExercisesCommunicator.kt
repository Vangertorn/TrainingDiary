package com.yankin.training_exercises.api.navigation

interface TrainingExercisesCommunicator {

    fun navigateTo(params: TrainingExercisesParams)

    companion object {
        const val NAV_KEY = "TrainingExercisesCommunicatorNavKey"
    }
}