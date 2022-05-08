package com.example.trainingdiary.support

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigateSave(navDirections: NavDirections, navOptions: NavOptions? = null) {
    val action = currentDestination?.getAction(navDirections.actionId)
    if (action != null) navigate(navDirections, navOptions)
}
