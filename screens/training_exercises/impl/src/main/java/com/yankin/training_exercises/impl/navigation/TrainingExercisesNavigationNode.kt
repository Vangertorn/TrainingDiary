package com.yankin.training_exercises.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import com.yankin.training_exercises.impl.presentation.TrainingExercisesFragment
import com.yankin.navigation.NavigationNode
import javax.inject.Inject

class TrainingExercisesNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            fragment<TrainingExercisesFragment>(ROUTE)
        }
    }

    companion object {
        const val ROUTE: String = "TrainingExercisesNavigationNode"
    }
}