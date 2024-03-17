package com.yankin.workout_routines.api.navigation

interface WorkoutRoutinesCommunicator {

    fun navigateTo(params: WorkoutRoutinesParams)

    companion object {
        const val NAV_KEY = "WorkoutRoutinesCommunicatorNavKey"
    }
}