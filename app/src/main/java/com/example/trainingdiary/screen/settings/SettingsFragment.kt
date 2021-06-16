package com.example.trainingdiary.screen.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.support.SupportFragmentInset
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentSettingsBinding
import com.example.trainingdiary.support.navigateSave
import org.koin.android.viewmodel.ext.android.viewModel


class SettingsFragment : SupportFragmentInset<FragmentSettingsBinding>(R.layout.fragment_settings) {
    override val viewBinding: FragmentSettingsBinding by viewBinding()
    private val viewModel: SettingsViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tvAutofill.setOnClickListener {
            findNavController().navigateSave(
                SettingsFragmentDirections.actionSettingsFragmentToExerciseAutofillFragment()
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
            if (viewBinding.etReoccurrence.text.toString().isEmpty()) {
                viewBinding.etReoccurrence.setText("1")
            } else {
                viewBinding.etReoccurrence.setText(
                    (viewBinding.etReoccurrence.text.toString().toInt() + 1).toString()
                )
            }

        }
        viewBinding.ibRemoveQuantity.setOnClickListener {
            if (viewBinding.etReoccurrence.text.toString().isEmpty()) {
                viewBinding.etReoccurrence.setText("0")
            } else {
                if (viewBinding.etReoccurrence.text.toString().toDouble() > 0) {
                    viewBinding.etReoccurrence.setText(
                        (viewBinding.etReoccurrence.text.toString().toInt() - 1).toString()
                    )
                } else {
                    viewBinding.etReoccurrence.setText("0")
                }

            }
        }

        viewModel.switchOrderLiveData.observe(this.viewLifecycleOwner) {
            viewBinding.switchSortTraining.isChecked = it
        }
        viewBinding.switchSortTraining.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewBinding.tvTrainingsLayout.text = "Last training above"
                viewModel.saveOrderAdded(true)

            } else {
                viewBinding.tvTrainingsLayout.text = "Last training below"
                viewModel.saveOrderAdded(false)
            }
        }


        viewBinding.ibAddWeight.setOnClickListener {
            if (viewBinding.etWeight.text.toString().isEmpty()) {
                viewBinding.etWeight.setText("1")
            } else {
                viewBinding.etWeight.setText(
                    (viewBinding.etWeight.text.toString().toDouble() + 1).toString()
                )
            }
        }
        viewBinding.ibRemoveWeight.setOnClickListener {
            if (viewBinding.etWeight.text.toString().isEmpty()) {
                viewBinding.etWeight.setText("0.0")
            } else {
                if (viewBinding.etWeight.text.toString().toDouble() >= 1) {
                    viewBinding.etWeight.setText(
                        (viewBinding.etWeight.text.toString().toDouble() - 1).toString()
                    )
                } else {
                    viewBinding.etWeight.setText("0.0")
                }

            }
        }
    }

    private fun addValues() {
        if (viewBinding.etReoccurrence.text.toString()
                .isBlank() && viewBinding.etWeight.text.toString().isBlank()
        ) {
            Toast.makeText(
                this.context,
                "The weight and reoccurrence fields are empty",
                Toast.LENGTH_SHORT
            ).show()
        } else if (viewBinding.etReoccurrence.text.toString()
                .isNotBlank() && viewBinding.etWeight.text.toString()
                .isBlank()
        ) {

            viewModel.saveReoccurrences(viewBinding.etReoccurrence.text.toString())

            Toast.makeText(
                this.context,
                "Reoccurrences were saved",
                Toast.LENGTH_SHORT
            ).show()
        } else if (viewBinding.etWeight.text.toString()
                .isNotBlank() && viewBinding.etReoccurrence.text.toString().isBlank()
        ) {

            viewModel.saveWeight(viewBinding.etWeight.text.toString())

            Toast.makeText(
                this.context,
                "Weight was saved",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.saveWeight(viewBinding.etWeight.text.toString())
            viewModel.saveReoccurrences(viewBinding.etReoccurrence.text.toString())
            Toast.makeText(
                this.context,
                "Default values were saved",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarSettings.setPadding(0, top, 0, 0)
    }
}