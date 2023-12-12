package com.yankin.trainingdiary.screen.exercise_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.databinding.FragmentExerciseListBinding
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import com.yankin.trainingdiary.support.SupportFragmentInset
import com.yankin.trainingdiary.support.SwipeCallback
import com.yankin.trainingdiary.support.extensions.navigateSave
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class ExerciseListFragment :
    SupportFragmentInset<FragmentExerciseListBinding>(R.layout.fragment_exercise_list) {
    override val viewBinding: FragmentExerciseListBinding by viewBinding()
    private val viewModel: ExerciseListViewModel by viewModels()
    private val args: ExerciseListFragmentArgs by navArgs()

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
                        findNavController().navigateSave(
                            ExerciseListFragmentDirections.actionExerciseListFragmentToApproachCreateBottomDialog2(
                                item.exercise
                            )
                        )
                    } else {
                        viewModel.rememberIdSuperSet((item as ViewHolderTypes.SuperSetDate).superSet)
                        findNavController().navigateSave(
                            ExerciseListFragmentDirections.actionExerciseListFragmentToSuperSetApproachCreateBottomDialog(
                                item.superSet.id
                            )
                        )
                    }
                }
            )
            viewBinding.recyclerView.adapter = adapter
        }

        val exerciseHelper = ItemTouchHelper(simpleCallback)
        exerciseHelper.attachToRecyclerView(viewBinding.recyclerView)

        args.training.let { training ->
            viewBinding.commentExerciseList.text = training.comment
            viewBinding.dateTrainingExerciseList.text = training.date
            viewBinding.muscleGroupsExerciseList.text = training.muscleGroups
            viewBinding.weightExerciseList.text =
                if (!training.weight.isNullOrEmpty()) if (training.muscleGroups.isNullOrEmpty()) getString(
                    R.string.weight, training.weight
                ) else getString(R.string.weight1, training.weight) else ""
        }
        viewBinding.toolbarExerciseList.setNavigationOnClickListener {
            viewModel.forgotIdTraining()
            findNavController().navigateSave(ExerciseListFragmentDirections.actionExerciseListFragmentToTrainingListFragment())
        }
        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigateSave(
                ExerciseListFragmentDirections.actionExerciseListFragmentToExerciseCreateBottomDialog(
                    args.training
                )
            )
        }
        viewBinding.ivEditTrainingExerciseList.setOnClickListener {
            findNavController().navigateSave(
                ExerciseListFragmentDirections.actionExerciseListFragmentToTrainingCreateBottomDialog(

                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    private fun delete(position: Int) {
        if (viewModel.getExerciseFromPosition(position) is ViewHolderTypes.ExerciseInfo) {
            val exercise =
                viewModel.getExerciseFromPosition(position) as ViewHolderTypes.ExerciseInfo
            viewModel.deletedExerciseTrue(exercise.exercise)
            Snackbar.make(
                viewBinding.recyclerView,
                getString(R.string.exercise_was_delete),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.deletedExerciseFalse(exercise.exercise)
                }.apply {
                    this.view.translationY = -savedInsets.bottom.toFloat()
                }.show()
        } else {
            val superSet =
                viewModel.getExerciseFromPosition(position) as ViewHolderTypes.SuperSetDate
            viewModel.deletedSuperSetTrue(superSet.superSet)
            Snackbar.make(
                viewBinding.recyclerView,
                getString(R.string.super_set_was_deleted),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.deletedSuperSetFalse(superSet.superSet)
                }.apply {
                    this.view.translationY = -savedInsets.bottom.toFloat()
                }.show()
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarExerciseList.setPadding(0, top, 0, 0)
    }
}
