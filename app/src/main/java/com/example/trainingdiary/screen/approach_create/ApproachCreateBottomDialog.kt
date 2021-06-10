package com.example.trainingdiary.screen.approach_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddApproachBinding
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ApproachCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetAddApproachBinding

    private val viewModel: ApproachCreateViewModel by viewModel()

    private val args: ApproachCreateBottomDialogArgs by navArgs()

    private val adapter = ApproachRecyclerViewAdapter(onClick = { approach ->
        viewModel.deleteApproach(approach)
    })

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
        viewBinding.rvApproach.adapter = adapter
        viewModel.approachLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.btnAddApproach.setOnClickListener {
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
            if (viewBinding.etReoccurrence.text.toString().isEmpty()) {
                viewBinding.etReoccurrence.setText("1")
            } else {
                viewBinding.etReoccurrence.setText(
                    (viewBinding.etReoccurrence.text.toString().toInt() + 1).toString()
                )
            }

        }
        viewBinding.ibRemoveQuantity.setOnClickListener {
            if (viewBinding.etReoccurrence.text.toString().isEmpty()) {
                viewBinding.etReoccurrence.setText("0")
            } else {
                if (viewBinding.etReoccurrence.text.toString().toDouble() > 0) {
                    viewBinding.etReoccurrence.setText(
                        (viewBinding.etReoccurrence.text.toString().toInt() - 1).toString()
                    )
                } else {
                    viewBinding.etReoccurrence.setText("0")
                }

            }
        }

        viewBinding.ibAddWeight.setOnClickListener {
            if (viewBinding.etWeight.text.toString().isEmpty()) {
                viewBinding.etWeight.setText("1")
            } else {
                viewBinding.etWeight.setText(
                    (viewBinding.etWeight.text.toString().toDouble() + 1).toString()
                )
            }
        }
        viewBinding.ibRemoveWeight.setOnClickListener {
            if (viewBinding.etWeight.text.toString().isEmpty()) {
                viewBinding.etWeight.setText("0.0")
            } else {
                if (viewBinding.etWeight.text.toString().toDouble() >= 1) {
                    viewBinding.etWeight.setText(
                        (viewBinding.etWeight.text.toString().toDouble() - 1).toString()
                    )
                } else {
                    viewBinding.etWeight.setText("0.0")
                }

            }
        }
    }



    override fun onPause() {
        super.onPause()
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
        }
    }




}