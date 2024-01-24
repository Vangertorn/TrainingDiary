package com.yankin.exercise_list.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import com.yankin.exercise_list.impl.presentation.ExerciseListFragment
import com.yankin.navigation.NavigationNode
import javax.inject.Inject

class ExerciseListNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            fragment<ExerciseListFragment>(ROUTE)
        }
    }

    companion object {
        const val ROUTE: String = "ExerciseListNavigationNode"
    }
}