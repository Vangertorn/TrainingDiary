package com.yankin.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

@SuppressLint("RestrictedApi")
fun NavController.navigateToDestination(destinationRoute: String, args: Bundle?) {
    findDestination(destinationRoute)?.id?.let { destinationId ->
        val navigationOptions = NavOptions.Builder()
            //            .setEnterAnim(R.anim.slide_in_right)
            //            .setExitAnim(R.anim.slide_out_left)
            //            .setPopEnterAnim(R.anim.slide_in_left)
            //            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        navigate(resId = destinationId, args = args, navOptions = navigationOptions)
    }
}