package com.yankin.exercise_create.impl.presentation.super_set_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.common.resource_import.CommonRString
import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.impl.navigation.SuperSetCreateParcelableParams
import com.yankin.exercise_create.impl.presentation.Exercise
import com.yankin.exercise_create.impl.presentation.ExerciseName
import com.yankin.exercise_list.api.navigation.ExerciseListCommunicator
import com.yankin.exercise_list.api.navigation.ExerciseListParams
import com.yankin.navigation.BundleParcelable
import com.yankin.trainingdiary.exercise_create.impl.R
import com.yankin.trainingdiary.exercise_create.impl.databinding.BottomSheetAddSetBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuperSetCreateBottomDialog : BottomSheetDialogFragment() {

    @Inject
    lateinit var exerciseListCommunicator: ExerciseListCommunicator

    private lateinit var viewBinding: BottomSheetAddSetBinding
    private val viewModel: SuperSetCreateViewModel by viewModels()
    private val params by BundleParcelable<SuperSetCreateParcelableParams>(ExerciseCreateCommunicator.NAV_KEY)
    private var selectedExercise: Exercise? = null
    private val adapter = SuperSetRecyclerViewAdapter(
        onClick = { exercise ->
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
        }
    )

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
                        idSet = params.superSetId
                    )
                )
                viewModel.addNewExerciseAutofill(
                    ExerciseName(
                        nameExercise = viewBinding.autoCompleteTvExerciseName.text.toString()
                    )
                )
                viewBinding.autoCompleteTvExerciseName.setText("")
                viewBinding.etCommentExercise.setText("")
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(CommonRString.exercise_name_is_empty),
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
            viewModel.createSuperSet(params.superSetId)
            val training = viewModel.getTraining()
            this.dismiss()
            exerciseListCommunicator.navigateTo(
                ExerciseListParams(
                    trainingId = training.id,
                    date = training.date,
                    muscleGroups = training.muscleGroups,
                    comment = training.comment,
                    weight = training.weight,
                    position = training.position,
                    deleted = training.deleted,
                    selectedMuscleGroup = training.selectedMuscleGroup,
                )
            )
        }
    }
}
