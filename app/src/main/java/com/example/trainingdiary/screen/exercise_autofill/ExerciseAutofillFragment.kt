package com.example.trainingdiary.screen.exercise_autofill

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.trainingdiary.support.SupportFragmentInset
import com.example.trainingdiary.support.setVerticalMargin
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentExerciseAutofillBinding
import com.example.trainingdiary.support.navigateSave
import org.koin.android.viewmodel.ext.android.viewModel

class ExerciseAutofillFragment :
    SupportFragmentInset<FragmentExerciseAutofillBinding>(R.layout.fragment_exercise_autofill) {
    override val viewBinding: FragmentExerciseAutofillBinding by viewBinding()
    private val viewModel: ExerciseAutofillViewModel by viewModel()
    private val adapter = ExerciseAutofillRecyclerViewAdapter(onClick = {
        findNavController().navigateSave(
            ExerciseAutofillFragmentDirections.actionExerciseAutofillFragmentToExerciseAutofillBottomDialog(
                it
            )
        )
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.recyclerView.adapter = adapter
        viewModel.autoCompleteExerciseLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.toolbarExerciseList.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarExerciseList.setPadding(0, top, 0, 0)
        viewBinding.recyclerView.setVerticalMargin(marginBottom = bottom)
    }

}