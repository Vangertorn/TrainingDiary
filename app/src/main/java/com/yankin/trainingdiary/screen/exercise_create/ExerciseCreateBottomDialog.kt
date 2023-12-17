package com.yankin.trainingdiary.screen.exercise_create

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yankin.trainingdiary.support.extensions.navigateSave
import com.yankin.trainingdiary.support.extensions.setVerticalMargin
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.databinding.BottomSheetAddExerciseBinding
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseName
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.support.SupportDialogFragmentInset
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseCreateBottomDialog :
    SupportDialogFragmentInset<BottomSheetAddExerciseBinding>(R.layout.bottom_sheet_add_exercise) {

    override val viewBinding: BottomSheetAddExerciseBinding by viewBinding()
    private val viewModel: ExerciseCreateViewModel by viewModels()
    private val args: ExerciseCreateBottomDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.autoCompleteExerciseStringLiveData.observe(this.viewLifecycleOwner) {
            val arrayAdapter: ArrayAdapter<String> =
                ArrayAdapter(requireContext(), R.layout.item_select_dialog, it)
            viewBinding.autoCompleteTvExerciseName.setAdapter(arrayAdapter)
        }

        viewBinding.btnAddToSet.setOnClickListener {
            if (viewBinding.autoCompleteTvExerciseName.text.isNotEmpty()) {
                val id = viewModel.saveSuperSet(
                    SuperSet(idTraining = args.training.id),
                    Exercise(
                        name = viewBinding.autoCompleteTvExerciseName.text.toString(),
                        comment = viewBinding.etCommentExercise.text.toString(),
                        idTraining = args.training.id
                    ),
                    Exercise(
                        name = "",
                        comment = "",
                        idTraining = args.training.id
                    )
                )
                viewModel.saveSuperSetId(id)
                viewModel.addNewExerciseAutofill(
                    ExerciseName(
                        nameExercise = viewBinding.autoCompleteTvExerciseName.text.toString()
                    )
                )
                this.dismiss()
                findNavController().navigateSave(
                    ExerciseCreateBottomDialogDirections.actionExerciseCreateBottomDialogToSuperSetCreateBottomDialog(
                        id
                    )
                )
            }
        }

        viewBinding.btnSave.setOnClickListener {
            if (viewBinding.autoCompleteTvExerciseName.text.isNotEmpty()) {
                viewModel.addNewExercise(
                    Exercise(
                        name = viewBinding.autoCompleteTvExerciseName.text.toString(),
                        idTraining = args.training.id,
                        comment = viewBinding.etCommentExercise.text.toString()
                    )
                )
                viewModel.addNewExerciseAutofill(
                    ExerciseName(
                        nameExercise = viewBinding.autoCompleteTvExerciseName.text.toString()
                    )
                )
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.exercise_name_is_empty),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.constrainLayout.setVerticalMargin(marginBottom = bottom)
    }
}
