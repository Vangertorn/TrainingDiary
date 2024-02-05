package com.yankin.settings.impl.presentation.settings

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yankin.common.addDouble
import com.yankin.common.addInt
import com.yankin.common.fragment.BaseFragment
import com.yankin.common.hideKeyboard
import com.yankin.common.removeDouble
import com.yankin.common.removeInt
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.view.setVerticalMargin
import com.yankin.common.viewbinding.viewBinding
import com.yankin.navigation.navigateToDestination
import com.yankin.settings.impl.navigation.SettingsNavigationNode.Companion.EXERCISE_AUTOFILL_FRAGMENT_DESTINATION
import com.yankin.settings.impl.navigation.SettingsNavigationNode.Companion.INFORMATION_FRAGMENT_DESTINATION
import com.yankin.settings.impl.presentation.MuscleGroup
import com.yankin.settings.impl.presentation.toModel
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    @Inject
    lateinit var navController: NavController

    override val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()
    private val adapter = SettingsMuscleGroupsRecyclerViewAdapter(
        onClick = { muscleGroup ->
            viewModel.deleteMuscleGroup(
                MuscleGroup(
                    id = muscleGroup.id,
                    nameMuscleGroup = muscleGroup.nameMuscleGroup,
                    factorySettings = muscleGroup.factorySettings,
                    deleted = true
                )
            )
        }
    )
    private var observerChek: Boolean = false
    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            binding.rvMuscleCroupsSettings.scrollToPosition(0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivInformation.setOnClickListener {
            navController.navigateToDestination(
                destinationRoute = INFORMATION_FRAGMENT_DESTINATION,
                args = null
            )
        }

        binding.ivReturnSettings.setOnClickListener {
            showRecoverDialog()
        }
        binding.rvMuscleCroupsSettings.adapter = adapter
        viewModel.muscleGroupLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it.map { it.toModel() })
        }
        if (!observerChek) {
            adapter.registerAdapterDataObserver(dataObserver)
            observerChek = true
        }
        binding.etMuscleGroups.setOnEditorActionListener { _, actionId, _ ->
            if (binding.etMuscleGroups.text.isNotBlank()) {
                viewModel.saveMuscleGroup(
                    MuscleGroup(
                        nameMuscleGroup = binding.etMuscleGroups.text.toString(),
                        factorySettings = false
                    )
                )
                binding.etMuscleGroups.setText("")
                this.hideKeyboard()
            } else {
                Toast.makeText(
                    this.context,
                    getString(CommonRString.the_name_muscle_group_is_empty),
                    Toast.LENGTH_SHORT
                ).show()
            }

            actionId == EditorInfo.IME_ACTION_DONE
        }

        binding.tvAutofill.setOnClickListener {
            navController.navigateToDestination(
                destinationRoute = EXERCISE_AUTOFILL_FRAGMENT_DESTINATION,
                args = null
            )
        }

        binding.toolbarSettings.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvAddValues.setOnClickListener {
            addValues()
        }
        viewModel.reoccurrencesLiveData.observe(this.viewLifecycleOwner) {
            binding.etReoccurrence.setText(it)
        }
        viewModel.weightLiveData.observe(this.viewLifecycleOwner) {
            binding.etWeight.setText(it)
        }
        binding.ibAddQuantity.setOnClickListener {
            binding.etReoccurrence.addInt(1)
        }
        binding.ibRemoveQuantity.setOnClickListener {
            binding.etReoccurrence.removeInt(1)
        }

        viewModel.switchOrderLiveData.observe(this.viewLifecycleOwner) {
            binding.switchSortTraining.isChecked = it
        }
        binding.switchSortTraining.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvTrainingsLayout.text = getString(CommonRString.Last_training_above)
                viewModel.saveOrderAdded(true)
            } else {
                binding.tvTrainingsLayout.text = getString(CommonRString.Last_training_below)
                viewModel.saveOrderAdded(false)
            }
        }

        binding.ibAddWeight.setOnClickListener {
            binding.etWeight.addDouble(1.0)
        }
        binding.ibRemoveWeight.setOnClickListener {
            binding.etWeight.removeDouble(1.0)
        }
    }

    private fun addValues() {
        if (binding.etReoccurrence.text.toString()
                .isBlank() && binding.etWeight.text.toString().isBlank()
        ) {
            Toast.makeText(
                this.context,
                getString(CommonRString.the_weight_and_reoccurrence_fields_are_empty),
                Toast.LENGTH_SHORT
            ).show()
        } else if (binding.etReoccurrence.text.toString()
                .isNotBlank() && binding.etWeight.text.toString()
                .isBlank()
        ) {

            viewModel.saveReoccurrences(binding.etReoccurrence.text.toString())

            Toast.makeText(
                this.context,
                getString(CommonRString.reoccurrences_were_saved),
                Toast.LENGTH_SHORT
            ).show()
        } else if (binding.etWeight.text.toString()
                .isNotBlank() && binding.etReoccurrence.text.toString().isBlank()
        ) {

            viewModel.saveWeight(binding.etWeight.text.toString())

            Toast.makeText(
                this.context,
                getString(CommonRString.weight_was_saved),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.saveWeight(binding.etWeight.text.toString())
            viewModel.saveReoccurrences(binding.etReoccurrence.text.toString())
            Toast.makeText(
                this.context,
                getString(CommonRString.default_values_were_saved),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showRecoverDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(CommonRString.title_recover_dialog))
            .setMessage(getString(CommonRString.message_recover_dialog))
            .setPositiveButton(getString(CommonRString.yes)) { dialog, _ ->
                viewModel.recoverValuesMuscleGroups()
                dialog.cancel()
            }.setNegativeButton(getString(CommonRString.no)) { dialog, _ ->
                dialog.cancel()
            }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.unregisterAdapterDataObserver(dataObserver)
        observerChek = false
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        binding.toolbarSettings.setPadding(0, top, 0, 0)
        binding.tvDateSave.setVerticalMargin(marginBottom = bottom)
    }
}
