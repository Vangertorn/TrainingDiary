package com.yankin.workout_routines.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import com.yankin.workout_routines.impl.presentation.WorkoutRoutinesFragment
import com.yankin.navigation.NavigationNode
import javax.inject.Inject

class WorkoutRoutinesNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            fragment<WorkoutRoutinesFragment>(ROUTE)
        }
    }

    companion object {
        const val ROUTE: String = "WorkoutRoutinesNavigationNode"
    }
}