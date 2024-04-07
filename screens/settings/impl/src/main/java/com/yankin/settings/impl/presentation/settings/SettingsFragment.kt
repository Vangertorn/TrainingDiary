package com.yankin.settings.impl.presentation.settings

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.debounce.debounceClick
import com.yankin.common.fragment.BaseFragment
import com.yankin.common.fragment.onBackPressed
import com.yankin.common.hideKeyboard
import com.yankin.common.view.setVerticalMargin
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedViewModelFactory
import com.yankin.kotlin.unsafeLazy
import com.yankin.navigation.navigateToDestination
import com.yankin.settings.impl.di.view_model_factories.SettingsViewModelFactory
import com.yankin.settings.impl.navigation.SettingsNavigationNode.Companion.EXERCISE_AUTOFILL_FRAGMENT_DESTINATION
import com.yankin.settings.impl.navigation.SettingsNavigationNode.Companion.INFORMATION_FRAGMENT_DESTINATION
import com.yankin.settings.impl.presentation.settings.adapter.MuscleGroupAdapter
import com.yankin.settings.impl.presentation.settings.models.SettingsEvent
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    @Inject
    lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: SettingsViewModelFactory

    override val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels {
        AssistedViewModelFactory(factory = viewModelFactory, owner = this)
    }
    private val adapter by unsafeLazy {
        MuscleGroupAdapter(onMuscleGroupClickListener = viewModel::onMuscleGroupClick)
    }
    private var observerCheck: Boolean = false
    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            binding.rvMuscleCroupsSettings.scrollToPosition(0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivInformation.debounceClick { viewModel.onInformationClick() }
        binding.ibAddQuantity.setOnClickListener { viewModel.onRepsCountUpClick() }
        binding.ibRemoveQuantity.setOnClickListener { viewModel.onRepsCountDownClick() }
        binding.ibAddWeight.setOnClickListener { viewModel.onWeightUpClick() }
        binding.ibRemoveWeight.setOnClickListener { viewModel.onWeightDownClick() }
        binding.ivReturnSettings.debounceClick { viewModel.onRecoverMuscleGroupsClick() }
        binding.etMuscleGroups.setOnEditorActionListener { _, actionId, _ ->
            viewModel.onSaveMuscleGroupClick()
            this.hideKeyboard()
            actionId == EditorInfo.IME_ACTION_DONE
        }
        binding.tvAutofill.setOnClickListener { viewModel.onSettingExercisePatternClick() }

        binding.toolbarSettings.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.tvAddValues.setOnClickListener { viewModel.onSaveDefaultValuesClick() }
        binding.switchSortTraining.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSwitchOrderClick(isChecked)
        }
        binding.etReoccurrence.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onRepsChange(text ?: "")
            }
        }
        binding.etWeight.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onWeightChange(text ?: "")
            }
        }
        binding.etMuscleGroups.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onMuscleGroupNameChange(text ?: "")
            }
        }

        onBackPressed {
           navController.popBackStack()
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)

        binding.rvMuscleCroupsSettings.adapter = adapter
        if (!observerCheck) {
            adapter.registerAdapterDataObserver(dataObserver)
            observerCheck = true
        }
    }

    override fun onObserveData() {
        viewModel.getSettingsUiStream().observeWithLifecycle(this) { uiState ->
            adapter.items = uiState.muscleGroupList
            binding.tvTrainingsLayout.text = uiState.trainingPositionDescription
            binding.switchSortTraining.isChecked = uiState.isLastTrainingTop
            if (uiState.weight != binding.etWeight.text?.toString()) {
                binding.etWeight.setText(uiState.weight)
            }
            if (uiState.reps != binding.etReoccurrence.text?.toString()) {
                binding.etReoccurrence.setText(uiState.reps)
            }
            if (uiState.muscleGroupNameByUser != binding.etMuscleGroups.text?.toString()) {
                binding.etMuscleGroups.setText(uiState.muscleGroupNameByUser)
            }
        }
        viewModel.getSettingsEventStream().observeWithLifecycle(this) { event ->
            when (event) {
                SettingsEvent.Default -> {}
                SettingsEvent.NavigateToInformation -> {
                    navController.navigateToDestination(
                        destinationRoute = INFORMATION_FRAGMENT_DESTINATION,
                        args = null
                    )
                    viewModel.onEventHandle()
                }

                is SettingsEvent.ShowInfoToast -> {
                    Toast.makeText(this.context, event.info, Toast.LENGTH_SHORT).show()
                    viewModel.onEventHandle()
                }

                is SettingsEvent.RecoverMuscleGroupsDialog -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(event.title)
                        .setMessage(event.message)
                        .setPositiveButton(event.positiveButton) { dialog, _ ->
                            viewModel.recoverValuesMuscleGroups()
                            dialog.cancel()
                        }.setNegativeButton(event.negativeButton) { dialog, _ ->
                            dialog.cancel()
                        }.show()
                    viewModel.onEventHandle()
                }

                SettingsEvent.NavigateToExercisePattern -> {
                    navController.navigateToDestination(
                        destinationRoute = EXERCISE_AUTOFILL_FRAGMENT_DESTINATION,
                        args = null
                    )
                    viewModel.onEventHandle()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.unregisterAdapterDataObserver(dataObserver)
        observerCheck = false
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        binding.toolbarSettings.setPadding(0, top, 0, 0)
        binding.tvDateSave.setVerticalMargin(marginBottom = bottom)
    }
}
