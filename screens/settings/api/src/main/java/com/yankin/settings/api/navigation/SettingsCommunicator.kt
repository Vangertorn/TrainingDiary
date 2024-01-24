package com.yankin.settings.api.navigation

interface SettingsCommunicator {

    fun navigateTo()

    companion object {
        const val NAV_KEY = "SettingsCommunicatorNavKey"
    }
}