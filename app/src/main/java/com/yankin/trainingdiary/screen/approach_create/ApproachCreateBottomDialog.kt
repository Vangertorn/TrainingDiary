package com.yankin.trainingdiary.screen.approach_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddApproachBinding
import com.example.trainingdiary.support.addDouble
import com.example.trainingdiary.support.addInt
import com.example.trainingdiary.support.chekDoubleEmpty
import com.example.trainingdiary.support.chekIntEmpty
import com.example.trainingdiary.support.removeDouble
import com.example.trainingdiary.support.removeInt
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.trainingdiary.models.Approach
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseAutofill
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApproachCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetAddApproachBinding

    private val viewModel: ApproachCreateViewModel by viewModels()

    private val args: ApproachCreateBottomDialogArgs by navArgs()

    private val adapter = ApproachRecyclerViewAdapter(onClick = { approach ->
        viewModel.deleteApproach(approach)
    })
    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            viewBinding.rvApproach.scrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetAddApproachBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_approach, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.autoCompleteExerciseLiveData.observe(this.viewLifecycleOwner) {
            val arrayAdapter: ArrayAdapter<String> =
                ArrayAdapter(requireContext(), R.layout.item_select_dialog, it)
            viewBinding.autoCompleteTvExerciseName.setAdapter(arrayAdapter)
        }

        viewBinding.rvApproach.adapter = adapter

        viewModel.reoccurrencesLiveData.observe(this.viewLifecycleOwner) {
            if (viewBinding.etReoccurrence.text.isBlank()) {
                viewBinding.etReoccurrence.setText(it)
            }
        }

        viewModel.weightLiveData.observe(this.viewLifecycleOwner) {
            if (viewBinding.etWeight.text.isBlank()) {
                viewBinding.etWeight.setText(it)
            }
        }

        viewModel.approachLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
            if (it.isNotEmpty()) {
                viewBinding.etWeight.setText(it[it.size - 1].weight)
                viewBinding.etReoccurrence.setText(it[it.size - 1].reoccurrences)
            }
        }

        adapter.registerAdapterDataObserver(dataObserver)

        viewBinding.btnEditExercise.setOnClickListener {
            if (viewBinding.autoCompleteTvExerciseName.text.isNotBlank()) {
                args.exercise.let {
                    viewModel.updateExercise(
                        Exercise(
                            id = it.id,
                            name = viewBinding.autoCompleteTvExerciseName.text.toString(),
                            idTraining = it.idTraining,
                            position = it.position,
                            comment = viewBinding.etCommentExercise.text.toString(),
                            deleted = it.deleted
                        )
                    )
                }
                viewModel.addNewExerciseAutofill(
                    ExerciseAutofill(
                        nameExercise = viewBinding.autoCompleteTvExerciseName.text.toString()
                    )
                )
            } else {
                Toast.makeText(
                    this.context,
                    getString(R.string.exercise_name_is_empty),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewBinding.btnAddApproach.setOnClickListener {
            viewBinding.etWeight.chekDoubleEmpty()
            viewBinding.etReoccurrence.chekIntEmpty()

            viewModel.addNewApproach(
                Approach(
                    weight = viewBinding.etWeight.text.toString(),
                    reoccurrences = viewBinding.etReoccurrence.text.toString(),
                    idExercise = args.exercise.id
                )
            )
        }
        args.exercise.let {
            viewBinding.autoCompleteTvExerciseName.setText(it.name)
            viewBinding.etCommentExercise.setText(it.comment)
        }


        viewBinding.ibAddQuantity.setOnClickListener {
            viewBinding.etReoccurrence.addInt(1)
        }
        viewBinding.ibRemoveQuantity.setOnClickListener {
            viewBinding.etReoccurrence.removeInt(1)
        }

        viewBinding.ibAddWeight.setOnClickListener {
            viewBinding.etWeight.addDouble(1.0)
        }
        viewBinding.ibRemoveWeight.setOnClickListener {
            viewBinding.etWeight.removeDouble(1.0)
        }
    }

    override fun onDestroyView() {
        adapter.unregisterAdapterDataObserver(dataObserver)
        super.onDestroyView()
    }
}