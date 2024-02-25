package com.yankin.exercise_list.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.yankin.approach_create.api.navigation.SetCreateCommunicator
import com.yankin.approach_create.api.navigation.SetCreateParams
import com.yankin.common.fragment.BaseFragment
import com.yankin.common.recyclerview.SwipeCallback
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.viewbinding.viewBinding
import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.api.navigation.ExerciseCreateParams
import com.yankin.exercise_list.api.navigation.ExerciseListCommunicator
import com.yankin.exercise_list.impl.navigation.ExerciseListParcelableParams
import com.yankin.exercise_list.impl.presentation.models.ViewHolderTypes
import com.yankin.navigation.BundleParcelable
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateParams
import com.yankin.trainingdiary.exercise_list.impl.R
import com.yankin.trainingdiary.exercise_list.impl.databinding.FragmentExerciseListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding>(R.layout.fragment_exercise_list) {

    @Inject
    lateinit var exerciseCreateCommunicator: ExerciseCreateCommunicator

    @Inject
    lateinit var trainingCreateCommunicator: TrainingCreateCommunicator

    @Inject
    lateinit var setCreateCommunicator: SetCreateCommunicator

    override val binding: FragmentExerciseListBinding by viewBinding(FragmentExerciseListBinding::bind)

    private val viewModel: ExerciseListViewModel by viewModels()
    private val params by BundleParcelable<ExerciseListParcelableParams>(ExerciseListCommunicator.NAV_KEY)

    @ExperimentalCoroutinesApi
    private val simpleCallback = SwipeCallback { position, direction ->
        when (direction) {
            ItemTouchHelper.LEFT -> {
                delete(position)
            }

            ItemTouchHelper.RIGHT -> {
                delete(position)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listLiveData.observe(this.viewLifecycleOwner) {
            val adapter = ExerciseRecyclerViewAdapter(
                it,
                onClick = { item ->
                    if (item is ViewHolderTypes.ExerciseInfo) {
                        viewModel.rememberIdExercise(item.exercise)
                        setCreateCommunicator.navigateToSetCreate(
                            SetCreateParams(exerciseId = item.exercise.id, superSetId = null)
                        )
                    } else {
                        setCreateCommunicator.navigateToSetCreate(
                            SetCreateParams(
                                exerciseId = null,
                                superSetId = (item as ViewHolderTypes.SuperSetDate).superSet.id
                            )
                        )
                    }
                }
            )
            binding.recyclerView.adapter = adapter
        }

        val exerciseHelper = ItemTouchHelper(simpleCallback)
        exerciseHelper.attachToRecyclerView(binding.recyclerView)

        params.let { training ->
            binding.commentExerciseList.text = training.comment
            binding.dateTrainingExerciseList.text = training.date
            binding.muscleGroupsExerciseList.text = training.muscleGroups
            binding.weightExerciseList.text =
                if (!training.weight.isNullOrEmpty()) if (training.muscleGroups.isNullOrEmpty()) getString(
                    CommonRString.weight, training.weight
                ) else getString(CommonRString.weight1, training.weight) else ""
        }
        binding.toolbarExerciseList.setNavigationOnClickListener {
            viewModel.forgotIdTraining()
            findNavController().popBackStack()
        }
        binding.btnAdd.setOnClickListener {
            exerciseCreateCommunicator.navigateToExerciseCreate(ExerciseCreateParams(params.trainingId))
        }
        binding.ivEditTrainingExerciseList.setOnClickListener {
            trainingCreateCommunicator.navigateTo(TrainingCreateParams.EditTraining(trainingId = params.trainingId))
        }
    }

    @ExperimentalCoroutinesApi
    private fun delete(position: Int) {
        if (viewModel.getExerciseFromPosition(position) is ViewHolderTypes.ExerciseInfo) {
            val exercise =
                viewModel.getExerciseFromPosition(position) as ViewHolderTypes.ExerciseInfo
            viewModel.deletedExerciseTrue(exercise.exercise)
            Snackbar.make(
                binding.recyclerView,
                getString(CommonRString.exercise_was_delete),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(CommonRString.undo)) {
                    viewModel.deletedExerciseFalse(exercise.exercise)
                }.apply {
                    this.view.translationY = -savedInsets.bottom.toFloat()
                }.show()
        } else {
            val superSet =
                viewModel.getExerciseFromPosition(position) as ViewHolderTypes.SuperSetDate
            viewModel.deletedSuperSetTrue(superSet.superSet)
            Snackbar.make(
                binding.recyclerView,
                getString(CommonRString.super_set_was_deleted),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(CommonRString.undo)) {
                    viewModel.deletedSuperSetFalse(superSet.superSet)
                }.apply {
                    this.view.translationY = -savedInsets.bottom.toFloat()
                }.show()
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        binding.toolbarExerciseList.setPadding(0, top, 0, 0)
    }
}
