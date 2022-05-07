package com.example.trainingdiary.screen.super_set_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddSetBinding
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.ExerciseAutofill
import com.example.trainingdiary.support.navigateSave

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class SuperSetCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetAddSetBinding
    private val viewModel: SuperSetCreateViewModel by viewModel()
    private val args: SuperSetCreateBottomDialogArgs by navArgs()
    private var selectedExercise: Exercise? = null
    private val adapter = SuperSetRecyclerViewAdapter(onClick = { exercise ->
        selectedExercise = exercise
        viewBinding.autoCompleteTvExerciseName.setText(exercise.name)
        viewBinding.etCommentExercise.setText(exercise.comment)
        if (exercise.name.isEmpty()) {
            viewBinding.btnDeleteFromSet.visibility = View.GONE
            viewBinding.btnAddToSet.visibility = View.VISIBLE
        } else {
            viewBinding.btnAddToSet.visibility = View.GONE
            viewBinding.btnDeleteFromSet.visibility = View.VISIBLE
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetAddSetBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_set, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.rvExerciseInSuperSet.adapter = adapter
        viewModel.exerciseInfoLiveDate.observe(this.viewLifecycleOwner) { exercise ->
            adapter.submitList(exercise)
        }
        viewBinding.btnAddToSet.setOnClickListener {
            if (viewBinding.autoCompleteTvExerciseName.text.isNotEmpty()) {
                viewModel.addNewExercise(
                    Exercise(
                        name = viewBinding.autoCompleteTvExerciseName.text.toString(),
                        comment = viewBinding.etCommentExercise.text.toString(),
                        idSet = args.superSetId
                    )
                )
                viewModel.addNewExerciseAutofill(
                    ExerciseAutofill(
                        nameExercise = viewBinding.autoCompleteTvExerciseName.text.toString()
                    )
                )
                viewBinding.autoCompleteTvExerciseName.setText("")
                viewBinding.etCommentExercise.setText("")
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.exercise_name_is_empty),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }
        viewBinding.btnDeleteFromSet.setOnClickListener {
            viewModel.deletedExercise(selectedExercise!!)
            adapter.selectedPositions = 0
            adapter.notifyItemChanged(0)
            viewBinding.autoCompleteTvExerciseName.setText("")
            viewBinding.etCommentExercise.setText("")
            viewBinding.btnDeleteFromSet.visibility = View.GONE
            viewBinding.btnAddToSet.visibility = View.VISIBLE
        }


        viewBinding.btnSave.setOnClickListener {
            viewModel.createSuperSet(args.superSetId)
            val training = viewModel.getTraining()
            this.dismiss()
            findNavController().navigateSave(
                SuperSetCreateBottomDialogDirections.actionSuperSetCreateBottomDialogToExerciseListFragment(
                    training
                )
            )

        }
    }
}