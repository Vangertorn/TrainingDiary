package com.example.trainingdiary.screen.exercise_list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.support.SupportFragmentInset
import com.example.myapplication.support.setVerticalMargin
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentExerciseListBinding
import com.example.trainingdiary.support.SwipeAndMoveCallback

import com.example.trainingdiary.support.navigateSave
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class ExerciseListFragment :
    SupportFragmentInset<FragmentExerciseListBinding>(R.layout.fragment_exercise_list) {
    override val viewBinding: FragmentExerciseListBinding by viewBinding()
    private val viewModel: ExerciseListViewModel by viewModel()
    private val args: ExerciseListFragmentArgs by navArgs()
    private val adapter = ExerciseRecyclerViewAdapter(
        onClick = {
            viewModel.rememberIdExercise(it.exercise)
            findNavController().navigateSave(
                ExerciseListFragmentDirections.actionExerciseListFragmentToApproachCreateBottomDialog2(
                    it.exercise
                )
            )
        }
    )
    private val dataObserverDesc = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            viewBinding.recyclerView.scrollToPosition(adapter.itemCount - 1)

        }
    }

    @ExperimentalCoroutinesApi
    private val simpleCallback = SwipeAndMoveCallback({ p1, p2 ->
        viewModel.switchExercisePosition(
            viewModel.getExerciseFromPosition(p1),
            viewModel.getExerciseFromPosition(p2)
        )
    },
        { position, direction ->
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    deleteExercise(position)
                }
                ItemTouchHelper.RIGHT -> {
                    deleteExercise(position)
                }
            }
        })

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerView.adapter = adapter
        viewModel.exerciseLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
        adapter.registerAdapterDataObserver(dataObserverDesc)

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
                    args.training
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.unregisterAdapterDataObserver(dataObserverDesc)
    }

    @ExperimentalCoroutinesApi
    private fun deleteExercise(position: Int) {
        val exercise = viewModel.getExerciseFromPosition(position)
        viewModel.deletedExerciseTrue(exercise)
        Snackbar.make(
            viewBinding.recyclerView,
            getString(R.string.exercise_was_delete),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.undo)) {
                viewModel.deletedExerciseFalse(exercise)
            }.apply {
                this.view.translationY = -savedInsets.bottom.toFloat()
            }.show()
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarExerciseList.setPadding(0, top, 0, 0)
        viewBinding.btnAdd.setVerticalMargin(0, bottom)
    }


}