package com.yankin.training_create.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog
import com.yankin.navigation.NavigationNode
import com.yankin.training_create.impl.presentation.TrainingCreateBottomDialog
import javax.inject.Inject

class TrainingCreateNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            dialog<TrainingCreateBottomDialog>(ROUTE)
        }
    }

    companion object {
        const val ROUTE: String = "TrainingCreateNavigationNode"
    }
}