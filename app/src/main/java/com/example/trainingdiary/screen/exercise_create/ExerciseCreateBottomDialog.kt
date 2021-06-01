package com.example.trainingdiary.screen.exercise_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddExerciseBinding
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.screen.exercise_list.ExerciseListFragmentArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel



class ExerciseCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetAddExerciseBinding
    private val viewModel: ExerciseCreateViewModel by viewModel()
    private val args: ExerciseCreateBottomDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = BottomSheetAddExerciseBinding.bind(LayoutInflater.from(context).inflate(R.layout.bottom_sheet_add_exercise, container,false))
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnSave.setOnClickListener {
            if(viewBinding.autoCompleteTvExerciseName.text.isNotEmpty()){
                viewModel.addNewExercise(Exercise(
                    name = viewBinding.autoCompleteTvExerciseName.text.toString(),
                    idTraining = args.training.id
                ))
            } else{
                Toast.makeText(
                    requireContext(),
                    "Exercise name  is empty",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}