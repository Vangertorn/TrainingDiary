package com.yankin.approach_create.api.navigation

interface SetCreateCommunicator {

    fun navigateToSetCreate(params: SetCreateParams)

    companion object {
        const val NAV_KEY = "ApproachCreateCommunicator"
    }
}