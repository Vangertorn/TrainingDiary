package com.yankin.exercise_create.impl.presentation.exercise_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.yankin.common.dialog.SupportDialogFragmentInset
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.view.setVerticalMargin
import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.impl.navigation.ExerciseCreateNavigationNode.Companion.SUPER_SET_DIALOG_DIALOG_DESTINATION
import com.yankin.exercise_create.impl.navigation.ExerciseCreateParcelableParams
import com.yankin.exercise_create.impl.navigation.SuperSetCreateParcelableParams
import com.yankin.exercise_create.impl.presentation.Exercise
import com.yankin.exercise_create.impl.presentation.exercisePattern
import com.yankin.exercise_create.impl.presentation.SuperSet
import com.yankin.navigation.BundleParcelable
import com.yankin.navigation.navigateToDestination
import com.yankin.trainingdiary.exercise_create.impl.R
import com.yankin.trainingdiary.exercise_create.impl.databinding.BottomSheetAddExerciseBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseCreateBottomDialog :
    SupportDialogFragmentInset<BottomSheetAddExerciseBinding>() {

    @Inject
    lateinit var navController: NavController

    lateinit var viewBinding: BottomSheetAddExerciseBinding
    private val viewModel: ExerciseCreateViewModel by viewModels()
    private val params by BundleParcelable<ExerciseCreateParcelableParams>(ExerciseCreateCommunicator.NAV_KEY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetAddExerciseBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_exercise, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.autoCompleteExerciseStringLiveData.observe(this.viewLifecycleOwner) {
            val arrayAdapter: ArrayAdapter<String> =
                ArrayAdapter(requireContext(), R.layout.item_select_dialog, it)
            viewBinding.autoCompleteTvexercisePattern.setAdapter(arrayAdapter)
        }

        viewBinding.btnAddToSet.setOnClickListener {
            if (viewBinding.autoCompleteTvexercisePattern.text.isNotEmpty()) {
                val id = viewModel.saveSuperSet(
                    SuperSet(idTraining = params.trainingId),
                    Exercise(
                        name = viewBinding.autoCompleteTvexercisePattern.text.toString(),
                        comment = viewBinding.etCommentExercise.text.toString(),
                        idTraining = params.trainingId
                    ),
                    Exercise(
                        name = "",
                        comment = "",
                        idTraining = params.trainingId
                    )
                )
                viewModel.saveSuperSetId(id)
                viewModel.addNewExerciseAutofill(
                    exercisePattern(
                        nameExercise = viewBinding.autoCompleteTvexercisePattern.text.toString()
                    )
                )
                this.dismiss()
                navController.navigateToDestination(
                    destinationRoute = SUPER_SET_DIALOG_DIALOG_DESTINATION,
                    args = bundleOf(ExerciseCreateCommunicator.NAV_KEY to SuperSetCreateParcelableParams(id))
                )
            }
        }

        viewBinding.btnSave.setOnClickListener {
            if (viewBinding.autoCompleteTvexercisePattern.text.isNotEmpty()) {
                viewModel.addNewExercise(
                    Exercise(
                        name = viewBinding.autoCompleteTvexercisePattern.text.toString(),
                        idTraining = params.trainingId,
                        comment = viewBinding.etCommentExercise.text.toString()
                    )
                )
                viewModel.addNewExerciseAutofill(
                    exercisePattern(
                        nameExercise = viewBinding.autoCompleteTvexercisePattern.text.toString()
                    )
                )
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(CommonRString.exercise_name_is_empty),
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
