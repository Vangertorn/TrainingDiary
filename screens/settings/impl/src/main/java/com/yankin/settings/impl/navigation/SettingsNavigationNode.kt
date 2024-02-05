package com.yankin.settings.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import com.yankin.navigation.NavigationNode
import com.yankin.settings.impl.presentation.exercise_pattern_create.ExercisePatternCreateBottomDialog
import com.yankin.settings.impl.presentation.exercise_pattern_list.ExerciseAutofillFragment
import com.yankin.settings.impl.presentation.information.InformationFragment
import com.yankin.settings.impl.presentation.settings.SettingsFragment
import javax.inject.Inject

class SettingsNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            navigation(startDestination = SETTINGS_FRAGMENT_DESTINATION, route = ROUTE) {
                fragment<SettingsFragment>(SETTINGS_FRAGMENT_DESTINATION)
                fragment<InformationFragment>(INFORMATION_FRAGMENT_DESTINATION)
                dialog<ExercisePatternCreateBottomDialog>(EXERCISE_AUTOFILL_DIALOG_DESTINATION)
                fragment<ExerciseAutofillFragment>(EXERCISE_AUTOFILL_FRAGMENT_DESTINATION)
            }
        }
    }

    companion object {
        private const val SETTINGS_FRAGMENT_DESTINATION = "SETTINGS_FRAGMENT_DESTINATION"
        const val INFORMATION_FRAGMENT_DESTINATION = "INFORMATION_FRAGMENT_DESTINATION"
        const val EXERCISE_AUTOFILL_FRAGMENT_DESTINATION = "EXERCISE_AUTOFILL_FRAGMENT_DESTINATION"
        const val EXERCISE_AUTOFILL_DIALOG_DESTINATION = "EXERCISE_AUTOFILL_DIALOG_DESTINATION"
        const val ROUTE: String = "SettingsNavigationNode"
    }
}