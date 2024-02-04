package com.yankin.membership.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.membership.api.navigation.MembershipCommunicator
import com.yankin.membership.api.navigation.MembershipParams
import com.yankin.membership.impl.navigation.MembershipNavigationNode.Companion.MEMBERSHIP_CREATE_DIALOG_DESTINATION
import com.yankin.membership.impl.navigation.MembershipNavigationNode.Companion.MEMBERSHIP_DIALOG_DESTINATION
import com.yankin.navigation.navigateToDestination
import javax.inject.Inject

class MembershipCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : MembershipCommunicator {

    override fun navigateToCreateMembership() {
        navController.navigateToDestination(
            destinationRoute = MEMBERSHIP_CREATE_DIALOG_DESTINATION,
            args = null,
        )
    }

    override fun navigateToMembership(params: MembershipParams) {
        navController.navigateToDestination(
            destinationRoute = MEMBERSHIP_DIALOG_DESTINATION,
            args = bundleOf(MembershipCommunicator.NAV_KEY to params.toParcelable()),
        )
    }

    private fun MembershipParams.toParcelable(): MembershipParcelableParams {
        return MembershipParcelableParams(
            membershipId = membershipId,
        )
    }
}