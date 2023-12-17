package com.yankin.trainingdiary.screen.exercise_autofill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.databinding.BottomSheetExerciseAutofillBinding
import com.yankin.trainingdiary.models.ExerciseName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseAutofillBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetExerciseAutofillBinding
    private val viewModel: ExerciseAutofillViewModel by viewModels()
    private val args: ExerciseAutofillBottomDialogArgs by navArgs()

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
        args.exerciseName.let {
            viewBinding.etCommentExercise.setText(it.nameExercise)
        }
        viewBinding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.btnDelete.setOnClickListener {
            viewModel.deleteExerciseAutofill(
                ExerciseName(
                    id = args.exerciseName.id,
                    nameExercise = args.exerciseName.nameExercise
                )
            )
            findNavController().popBackStack()
        }
        viewBinding.btnSave.setOnClickListener {
            viewModel.updateExerciseAutoFill(
                ExerciseName(
                    id = args.exerciseName.id,
                    nameExercise = viewBinding.etCommentExercise.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }
}
