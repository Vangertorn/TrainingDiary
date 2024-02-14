package com.yankin.approach_create.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.approach_create.api.navigation.ApproachCreateCommunicator
import com.yankin.approach_create.api.navigation.ApproachCreateParams
import com.yankin.navigation.navigateToDestination
import javax.inject.Inject

class ApproachCreateCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : ApproachCreateCommunicator {

    override fun navigateToApproachCreate(params: ApproachCreateParams) {
        navController.navigateToDestination(
            destinationRoute = ApproachCreateNavigationNode.APPROACH_CREATE_DIALOG_DESTINATION,
            args = bundleOf(ApproachCreateCommunicator.NAV_KEY to params.toParcelable()),
        )
    }

    override fun navigateToSuperSetApproachCreate() {
        navController.navigateToDestination(
            destinationRoute = ApproachCreateNavigationNode.SUPER_SET_APPROACH_CREATE_DIALOG_DESTINATION,
            args = null,
        )
    }

    private fun ApproachCreateParams.toParcelable(): ApproachCreateParcelableParams {
        return ApproachCreateParcelableParams(
            exerciseId = exerciseId,
            name = name,
            trainingId = trainingId,
            position = position,
            comment = comment,
            deleted = deleted,
            idSet = idSet,
        )
    }
}