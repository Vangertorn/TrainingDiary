package com.yankin.approach_create.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog
import com.yankin.approach_create.impl.presentation.approach_create.ApproachCreateBottomDialog
import com.yankin.approach_create.impl.presentation.super_set_approach_create.SuperSetApproachCreateBottomDialog
import com.yankin.navigation.NavigationNode
import javax.inject.Inject

class ApproachCreateNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            dialog<ApproachCreateBottomDialog>(APPROACH_CREATE_DIALOG_DESTINATION)
            dialog<SuperSetApproachCreateBottomDialog>(SUPER_SET_APPROACH_CREATE_DIALOG_DESTINATION)
        }
    }

    companion object {
        const val APPROACH_CREATE_DIALOG_DESTINATION = "APPROACH_CREATE_DIALOG_DESTINATION"
        const val SUPER_SET_APPROACH_CREATE_DIALOG_DESTINATION = "SUPER_SET_APPROACH_CREATE_DIALOG_DESTINATION"
    }
}