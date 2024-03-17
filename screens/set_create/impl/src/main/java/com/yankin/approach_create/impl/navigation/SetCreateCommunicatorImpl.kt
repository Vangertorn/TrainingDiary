package com.yankin.approach_create.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.approach_create.api.navigation.SetCreateCommunicator
import com.yankin.approach_create.api.navigation.SetCreateParams
import com.yankin.navigation.navigateToDestination
import javax.inject.Inject

class SetCreateCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : SetCreateCommunicator {

    override fun navigateToSetCreate(params: SetCreateParams) {
        navController.navigateToDestination(
            destinationRoute = SetCreateNavigationNode.SET_CREATE_DIALOG_DESTINATION,
            args = bundleOf(SetCreateCommunicator.NAV_KEY to params.toParcelable()),
        )
    }

    private fun SetCreateParams.toParcelable(): SetCreateParcelableParams {
        return SetCreateParcelableParams(trainingBlockId = trainingBlockId)
    }
}