package com.example.trainingdiary.screen.exercise_list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.support.SupportFragmentInset
import com.example.myapplication.support.setVerticalMargin
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentExerciseListBinding
import com.example.trainingdiary.screen.approach_create.ApproachCreateBottomDialog
import com.example.trainingdiary.screen.exercise_create.ExerciseCreateBottomDialog
import org.koin.android.viewmodel.ext.android.viewModel

class ExerciseListFragment :
    SupportFragmentInset<FragmentExerciseListBinding>(R.layout.fragment_exercise_list) {
    override val viewBinding: FragmentExerciseListBinding by viewBinding()
    private val viewModel: ExerciseListViewModel by viewModel()
    private val args: ExerciseListFragmentArgs by navArgs()
    private val adapter = ExerciseRecyclerViewAdapter(
        onClick = {
            val bottomFragment = ApproachCreateBottomDialog()
            bottomFragment.show(childFragmentManager, "1")
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerView.adapter = adapter
        viewModel.exerciseLiveData.observe(this.viewLifecycleOwner){
            adapter.submitList(it)
        }
        args.training.let {
            viewBinding.commentExerciseList.text = it.comment
            viewBinding.dateTrainingExerciseList.text = it.date
            viewBinding.muscleGroupsExerciseList.text = it.muscleGroups
            viewBinding.weightExerciseList.text = it.weight
        }
        viewBinding.toolbarExerciseList.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.btnAdd.setOnClickListener {
            val bottomFragment = ExerciseCreateBottomDialog()
            bottomFragment.show(childFragmentManager, "2")
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarExerciseList.setVerticalMargin(top)
        viewBinding.btnAdd.setVerticalMargin(0, bottom)
    }


}