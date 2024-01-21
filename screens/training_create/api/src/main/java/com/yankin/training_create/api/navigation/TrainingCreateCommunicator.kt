package com.yankin.training_create.api.navigation

interface TrainingCreateCommunicator {

    fun navigateTo(params: TrainingCreateParams)

    companion object {
        const val NAV_KEY = "TrainingCreateCommunicatorNavKey"
    }
}