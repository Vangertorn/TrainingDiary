package com.yankin.trainingdiary.screen.exercise_autofill

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.databinding.FragmentExerciseAutofillBinding
import com.yankin.trainingdiary.support.SupportFragmentInset
import com.yankin.trainingdiary.support.extensions.navigateSave
import com.yankin.trainingdiary.support.extensions.setVerticalMargin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseAutofillFragment :
    SupportFragmentInset<FragmentExerciseAutofillBinding>(R.layout.fragment_exercise_autofill) {
    override val viewBinding: FragmentExerciseAutofillBinding by viewBinding()
    private val viewModel: ExerciseAutofillViewModel by viewModels()
    private val adapter = ExerciseAutofillRecyclerViewAdapter(
        onClick = {
            findNavController().navigateSave(
                ExerciseAutofillFragmentDirections.actionExerciseAutofillFragmentToExerciseAutofillBottomDialog(
                    it
                )
            )
        }
    )

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
