package com.yankin.training_list.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import com.yankin.navigation.NavigationNode
import com.yankin.training_list.impl.presentation.TrainingListFragment
import javax.inject.Inject

class TrainingListNavigationNode @Inject constructor(): NavigationNode{

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            fragment<TrainingListFragment>(ROUTE)
        }
    }

    companion object{
        const val ROUTE: String = "TrainingListNavigationNode"
    }
}