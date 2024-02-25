package com.yankin.approach_create.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog
import com.yankin.approach_create.impl.presentation.SetCreateBottomDialog
import com.yankin.navigation.NavigationNode
import javax.inject.Inject

class SetCreateNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            dialog<SetCreateBottomDialog>(SET_CREATE_DIALOG_DESTINATION)
        }
    }

    companion object {
        const val SET_CREATE_DIALOG_DESTINATION = "APPROACH_CREATE_DIALOG_DESTINATION"
    }
}