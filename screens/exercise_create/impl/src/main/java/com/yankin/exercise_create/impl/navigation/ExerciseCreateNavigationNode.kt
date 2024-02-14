package com.yankin.exercise_create.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog
import com.yankin.exercise_create.impl.presentation.exercise_create.ExerciseCreateBottomDialog
import com.yankin.navigation.NavigationNode
import javax.inject.Inject

class ExerciseCreateNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            dialog<ExerciseCreateBottomDialog>(EXERCISE_CREATE_DIALOG_DESTINATION)
        }
    }

    companion object {
        const val EXERCISE_CREATE_DIALOG_DESTINATION = "EXERCISE_CREATE_DIALOG_DESTINATION"
    }
}