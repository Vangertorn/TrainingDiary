package com.yankin.training_create.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.navigation.navigateWithAnimation
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateParams
import javax.inject.Inject

class TrainingCreateCommunicatorImpl @Inject constructor(
    private val navController: NavController
): TrainingCreateCommunicator {

    override fun navigateTo(params: TrainingCreateParams){
        navController.navigateWithAnimation(
            route = TrainingCreateNavigationNode.ROUTE,
            args = bundleOf(TrainingCreateCommunicator.NAV_KEY to params.toParcelable())
        )
    }

    private fun TrainingCreateParams.toParcelable(): TrainingCreateParcelableParams {
        return TrainingCreateParcelableParams(trainingId = trainingId)
    }
}