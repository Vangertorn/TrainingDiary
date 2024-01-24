package com.yankin.approach_create.api.navigation

interface ApproachCreateCommunicator {

    fun navigateToApproachCreate(params: ApproachCreateParams)

    fun navigateToSuperSetApproachCreate()

    companion object {
        const val NAV_KEY = "ApproachCreateCommunicator"
    }
}