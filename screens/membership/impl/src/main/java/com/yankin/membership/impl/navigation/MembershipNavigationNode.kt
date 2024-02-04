package com.yankin.membership.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog
import com.yankin.navigation.NavigationNode
import com.yankin.membership.impl.presentation.membership.MembershipCreateBottomDialog
import com.yankin.membership.impl.presentation.membership_info.MembershipBottomDialog
import javax.inject.Inject

class MembershipNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            dialog<MembershipCreateBottomDialog>(MEMBERSHIP_CREATE_DIALOG_DESTINATION)
            dialog<MembershipBottomDialog>(MEMBERSHIP_DIALOG_DESTINATION)
        }
    }

    companion object {
        const val MEMBERSHIP_CREATE_DIALOG_DESTINATION = "MEMBERSHIP_CREATE_DIALOG_DESTINATION"
        const val MEMBERSHIP_DIALOG_DESTINATION = "MEMBERSHIP_DIALOG_DESTINATION"
    }
}