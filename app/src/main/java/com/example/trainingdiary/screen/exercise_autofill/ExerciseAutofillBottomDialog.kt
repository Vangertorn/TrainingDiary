package com.example.trainingdiary.screen.exercise_autofill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetExerciseAutofillBinding
import com.example.trainingdiary.models.ExerciseAutofill
import com.example.trainingdiary.screen.exercise_create.ExerciseCreateBottomDialogArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ExerciseAutofillBottomDialog : BottomSheetDialogFragment() {


    private lateinit var viewBinding: BottomSheetExerciseAutofillBinding
    private val viewModel: ExerciseAutofillViewModel by viewModel()
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
        args.exerciseAutofill.let {
            viewBinding.etCommentExercise.setText(it.nameExercise)
        }
        viewBinding.btnLose.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.btnDelete.setOnClickListener {
            viewModel.deleteExerciseAutofill(
                ExerciseAutofill(
                    id = args.exerciseAutofill.id,
                    nameExercise = args.exerciseAutofill.nameExercise
                )
            )
            findNavController().popBackStack()
        }
        viewBinding.btnSave.setOnClickListener {
            viewModel.updateExerciseAutoFill(
                ExerciseAutofill(
                    id = args.exerciseAutofill.id,
                    nameExercise = viewBinding.etCommentExercise.text.toString()
                )
            )
            findNavController().popBackStack()
        }

    }
}