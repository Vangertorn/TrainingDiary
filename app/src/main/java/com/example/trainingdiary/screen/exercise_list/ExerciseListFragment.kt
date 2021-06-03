package com.example.trainingdiary.screen.exercise_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.support.SupportFragmentInset
import com.example.myapplication.support.setVerticalMargin
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentExerciseListBinding
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.support.SwipeCallback
import com.example.trainingdiary.support.navigateSave
import com.google.android.material.chip.Chip
import org.koin.android.viewmodel.ext.android.viewModel

class ExerciseListFragment :
    SupportFragmentInset<FragmentExerciseListBinding>(R.layout.fragment_exercise_list) {
    override val viewBinding: FragmentExerciseListBinding by viewBinding()
    private val viewModel: ExerciseListViewModel by viewModel()
    private val args: ExerciseListFragmentArgs by navArgs()
    private val adapter = ExerciseRecyclerViewAdapter(
        onClick = {
            viewModel.rememberIdExercise(it)
            findNavController().navigateSave(
                ExerciseListFragmentDirections.actionExerciseListFragmentToApproachCreateBottomDialog2(
                    it
                )
            )
        }
    )
    private val simpleCallback = SwipeCallback { position, direction ->
        when (direction) {
            ItemTouchHelper.LEFT -> {

            }
            ItemTouchHelper.RIGHT -> {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerView.adapter = adapter
        viewModel.exerciseLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)

//        viewModel.approachLiveData.observe(this.viewLifecycleOwner) {
//            for (approach in it) {
//
//            }
        }
        val noteHelper = ItemTouchHelper(simpleCallback)
        noteHelper.attachToRecyclerView(viewBinding.recyclerView)
        args.training.let {
            viewBinding.commentExerciseList.text = it.comment
            viewBinding.dateTrainingExerciseList.text = it.date
            viewBinding.muscleGroupsExerciseList.text = it.muscleGroups
            viewBinding.weightExerciseList.text = it.weight
        }
        viewBinding.toolbarExerciseList.setNavigationOnClickListener {
            viewModel.forgotIdTraining()
            findNavController().popBackStack()
        }
        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigateSave(
                ExerciseListFragmentDirections.actionExerciseListFragmentToExerciseCreateBottomDialog(
                    args.training
                )
            )
        }
    }

//    private fun addNewChip(approach: Approach){
//        val keyword = approach.weight + approach.reoccurrences
//        val newChip: Chip = LayoutInflater.from(context).inflate(R.layout.chip_approaches, this.adapter.cgApproachGroup)
//    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarExerciseList.setVerticalMargin(top)
        viewBinding.btnAdd.setVerticalMargin(0, bottom)
    }


}