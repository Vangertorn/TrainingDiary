package com.yankin.settings.impl.navigation

import androidx.navigation.NavController
import com.yankin.navigation.navigateToDestination
import com.yankin.settings.api.navigation.SettingsCommunicator
import javax.inject.Inject

class SettingsCommunicatorImpl @Inject constructor(
    private val navController: NavController
): SettingsCommunicator {

    override fun navigateTo(){
        navController.navigateToDestination(
            destinationRoute = SettingsNavigationNode.ROUTE,
            args = null,
        )
    }
}