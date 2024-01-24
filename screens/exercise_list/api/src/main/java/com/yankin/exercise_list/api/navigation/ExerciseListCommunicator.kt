package com.yankin.exercise_list.api.navigation

interface ExerciseListCommunicator {

    fun navigateTo(params: ExerciseListParams)

    companion object {
        const val NAV_KEY = "ExerciseListCommunicatorNavKey"
    }
}