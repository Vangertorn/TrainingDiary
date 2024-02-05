package com.yankin.settings.impl.presentation.exercise_pattern_list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.fragment.BaseFragment
import com.yankin.common.view.setVerticalMargin
import com.yankin.common.viewbinding.viewBinding
import com.yankin.kotlin.unsafeLazy
import com.yankin.navigation.navigateToDestination
import com.yankin.settings.api.navigation.SettingsCommunicator
import com.yankin.settings.impl.navigation.SettingsNavigationNode
import com.yankin.settings.impl.presentation.exercise_pattern_list.adapter.ExercisePatternAdapter
import com.yankin.settings.impl.presentation.exercise_pattern_list.models.ExercisePatternListEvent
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.FragmentExerciseAutofillBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExercisePatternListFragment : BaseFragment<FragmentExerciseAutofillBinding>(R.layout.fragment_exercise_autofill) {

    @Inject
    lateinit var navController: NavController

    override val binding by viewBinding(FragmentExerciseAutofillBinding::bind)

    private val viewModel: ExercisePatternListViewModel by viewModels()
    private val adapter by unsafeLazy {
        ExercisePatternAdapter(onExercisePatternClickListener = viewModel::onExercisePatternClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarExerciseList.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
    }

    override fun onObserveData() {
        viewModel.getExercisePatternListUiStream().observeWithLifecycle(this) { uiState ->
            adapter.items = uiState.exercisePatternList
        }
        viewModel.getExercisePatternListEventStream().observeWithLifecycle(this) { event ->
            when (event) {
                ExercisePatternListEvent.Default -> {}
                is ExercisePatternListEvent.NavigateToCreateExercisePattern -> {
                    navController.navigateToDestination(
                        destinationRoute = SettingsNavigationNode.EXERCISE_AUTOFILL_DIALOG_DESTINATION,
                        args = bundleOf(SettingsCommunicator.NAV_KEY to event.params),
                    )
                    viewModel.onEventHandle()
                }
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        binding.toolbarExerciseList.setPadding(0, top, 0, 0)
        binding.recyclerView.setVerticalMargin(marginBottom = bottom)
    }
}
