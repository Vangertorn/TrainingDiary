package com.yankin.settings.impl.presentation.exercise_pattern_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.navigation.BundleParcelable
import com.yankin.settings.api.navigation.SettingsCommunicator
import com.yankin.settings.impl.navigation.ExerciseAutofillDialogParams
import com.yankin.settings.impl.presentation.exercisePattern
import com.yankin.settings.impl.presentation.exercise_pattern_list.ExerciseAutofillViewModel
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.BottomSheetExerciseAutofillBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisePatternCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetExerciseAutofillBinding
    private val viewModel: ExerciseAutofillViewModel by viewModels()
    private val params by BundleParcelable<ExerciseAutofillDialogParams>(SettingsCommunicator.NAV_KEY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = BottomSheetExerciseAutofillBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_exercise_autofill, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        params.exercisePattern.let {
            viewBinding.etCommentExercise.setText(it.nameExercise)
        }
        viewBinding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.btnDelete.setOnClickListener {
            viewModel.deleteExerciseAutofill(
                exercisePattern(
                    id = params.exercisePattern.id,
                    nameExercise = params.exercisePattern.nameExercise
                )
            )
            findNavController().popBackStack()
        }
        viewBinding.btnSave.setOnClickListener {
            viewModel.updateExerciseAutoFill(
                exercisePattern(
                    id = params.exercisePattern.id,
                    nameExercise = viewBinding.etCommentExercise.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }
}
