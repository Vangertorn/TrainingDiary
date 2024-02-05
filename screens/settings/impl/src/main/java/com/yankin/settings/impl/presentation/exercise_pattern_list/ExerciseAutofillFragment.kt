package com.yankin.settings.impl.presentation.exercise_pattern_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.yankin.common.fragment.SupportFragmentInset
import com.yankin.common.view.setVerticalMargin
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.navigation.navigateToDestination
import com.yankin.settings.api.navigation.SettingsCommunicator
import com.yankin.settings.impl.navigation.ExerciseAutofillDialogParams
import com.yankin.settings.impl.navigation.SettingsNavigationNode
import com.yankin.settings.impl.presentation.toModel
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.FragmentExerciseAutofillBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseAutofillFragment :
    SupportFragmentInset<FragmentExerciseAutofillBinding>(R.layout.fragment_exercise_autofill) {

    @Inject
    lateinit var navController: NavController

    override lateinit var viewBinding: FragmentExerciseAutofillBinding
    private val viewModel: ExerciseAutofillViewModel by viewModels()
    private val adapter = ExerciseAutofillRecyclerViewAdapter(
        onClick = {
            navController.navigateToDestination(
                destinationRoute = SettingsNavigationNode.EXERCISE_AUTOFILL_DIALOG_DESTINATION,
                args = bundleOf(SettingsCommunicator.NAV_KEY to ExerciseAutofillDialogParams(it)),
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentExerciseAutofillBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.fragment_exercise_autofill, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.recyclerView.adapter = adapter
        viewModel.autoCompleteExerciseLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it.map(ExercisePatternDomain::toModel))
        }

        viewBinding.toolbarExerciseList.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarExerciseList.setPadding(0, top, 0, 0)
        viewBinding.recyclerView.setVerticalMargin(marginBottom = bottom)
    }
}
