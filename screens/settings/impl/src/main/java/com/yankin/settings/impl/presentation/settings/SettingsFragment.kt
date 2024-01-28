package com.yankin.settings.impl.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yankin.common.addDouble
import com.yankin.common.addInt
import com.yankin.common.fragment.SupportFragmentInset
import com.yankin.common.hideKeyboard
import com.yankin.common.removeDouble
import com.yankin.common.removeInt
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.view.setVerticalMargin
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
class SettingsFragment : SupportFragmentInset<FragmentSettingsBinding>(R.layout.fragment_settings) {

    @Inject
    lateinit var navController: NavController

    override lateinit var viewBinding: FragmentSettingsBinding
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
            viewBinding.rvMuscleCroupsSettings.scrollToPosition(0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSettingsBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.fragment_settings, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.ivInformation.setOnClickListener {
            navController.navigateToDestination(
                destinationRoute = INFORMATION_FRAGMENT_DESTINATION,
                args = null
            )
        }

        viewBinding.ivReturnSettings.setOnClickListener {
            showRecoverDialog()
        }
        viewBinding.rvMuscleCroupsSettings.adapter = adapter
        viewModel.muscleGroupLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it.map { it.toModel() })
        }
        if (!observerChek) {
            adapter.registerAdapterDataObserver(dataObserver)
            observerChek = true
        }
        viewBinding.etMuscleGroups.setOnEditorActionListener { _, actionId, _ ->
            if (viewBinding.etMuscleGroups.text.isNotBlank()) {
                viewModel.saveMuscleGroup(
                    MuscleGroup(
                        nameMuscleGroup = viewBinding.etMuscleGroups.text.toString(),
                        factorySettings = false
                    )
                )
                viewBinding.etMuscleGroups.setText("")
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

        viewBinding.tvAutofill.setOnClickListener {
            navController.navigateToDestination(
                destinationRoute = EXERCISE_AUTOFILL_FRAGMENT_DESTINATION,
                args = null
            )
        }

        viewBinding.toolbarSettings.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.tvAddValues.setOnClickListener {
            addValues()
        }
        viewModel.reoccurrencesLiveData.observe(this.viewLifecycleOwner) {
            viewBinding.etReoccurrence.setText(it)
        }
        viewModel.weightLiveData.observe(this.viewLifecycleOwner) {
            viewBinding.etWeight.setText(it)
        }
        viewBinding.ibAddQuantity.setOnClickListener {
            viewBinding.etReoccurrence.addInt(1)
        }
        viewBinding.ibRemoveQuantity.setOnClickListener {
            viewBinding.etReoccurrence.removeInt(1)
        }

        viewModel.switchOrderLiveData.observe(this.viewLifecycleOwner) {
            viewBinding.switchSortTraining.isChecked = it
        }
        viewBinding.switchSortTraining.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewBinding.tvTrainingsLayout.text = getString(CommonRString.Last_training_above)
                viewModel.saveOrderAdded(true)
            } else {
                viewBinding.tvTrainingsLayout.text = getString(CommonRString.Last_training_below)
                viewModel.saveOrderAdded(false)
            }
        }

        viewBinding.ibAddWeight.setOnClickListener {
            viewBinding.etWeight.addDouble(1.0)
        }
        viewBinding.ibRemoveWeight.setOnClickListener {
            viewBinding.etWeight.removeDouble(1.0)
        }
    }

    private fun addValues() {
        if (viewBinding.etReoccurrence.text.toString()
                .isBlank() && viewBinding.etWeight.text.toString().isBlank()
        ) {
            Toast.makeText(
                this.context,
                getString(CommonRString.the_weight_and_reoccurrence_fields_are_empty),
                Toast.LENGTH_SHORT
            ).show()
        } else if (viewBinding.etReoccurrence.text.toString()
                .isNotBlank() && viewBinding.etWeight.text.toString()
                .isBlank()
        ) {

            viewModel.saveReoccurrences(viewBinding.etReoccurrence.text.toString())

            Toast.makeText(
                this.context,
                getString(CommonRString.reoccurrences_were_saved),
                Toast.LENGTH_SHORT
            ).show()
        } else if (viewBinding.etWeight.text.toString()
                .isNotBlank() && viewBinding.etReoccurrence.text.toString().isBlank()
        ) {

            viewModel.saveWeight(viewBinding.etWeight.text.toString())

            Toast.makeText(
                this.context,
                getString(CommonRString.weight_was_saved),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.saveWeight(viewBinding.etWeight.text.toString())
            viewModel.saveReoccurrences(viewBinding.etReoccurrence.text.toString())
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
        viewBinding.toolbarSettings.setPadding(0, top, 0, 0)
        viewBinding.tvDateSave.setVerticalMargin(marginBottom = bottom)
    }
}
