package com.yankin.approach_create.impl.presentation.approach_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.approach_create.api.navigation.ApproachCreateCommunicator
import com.yankin.approach_create.impl.navigation.ApproachCreateParcelableParams
import com.yankin.approach_create.impl.presentation.Approach
import com.yankin.approach_create.impl.presentation.Exercise
import com.yankin.approach_create.impl.presentation.exercisePattern
import com.yankin.common.addDouble
import com.yankin.common.addInt
import com.yankin.common.chekDoubleEmpty
import com.yankin.common.chekIntEmpty
import com.yankin.common.removeDouble
import com.yankin.common.removeInt
import com.yankin.common.resource_import.CommonRString
import com.yankin.navigation.BundleParcelable
import com.yankin.trainingdiary.approach_create.impl.R
import com.yankin.trainingdiary.approach_create.impl.databinding.BottomSheetAddApproachBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApproachCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetAddApproachBinding

    private val viewModel: ApproachCreateViewModel by viewModels()

    private val params by BundleParcelable<ApproachCreateParcelableParams>(ApproachCreateCommunicator.NAV_KEY)

    private val adapter = ApproachRecyclerViewAdapter(
        onClick = { approach ->
            viewModel.deleteApproach(approach)
        }
    )
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
            viewBinding.autoCompleteTvexercisePattern.setAdapter(arrayAdapter)
        }

        viewBinding.rvApproach.adapter = adapter

        viewModel.reoccurrencesLiveData.observe(this.viewLifecycleOwner) {
            if (viewBinding.etReoccurrence.text.isBlank()) {
                viewBinding.etReoccurrence.setText(it)
            }
        }

        viewModel.weightLiveData.observe(this.viewLifecycleOwner) {
            if (viewBinding.etWeight.text.isBlank()) {
                viewBinding.etWeight.setText(it.toString())
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
            if (viewBinding.autoCompleteTvexercisePattern.text.isNotBlank()) {
                params.let {
                    viewModel.updateExercise(
                        Exercise(
                            id = it.exerciseId,
                            name = viewBinding.autoCompleteTvexercisePattern.text.toString(),
                            idTraining = it.trainingId,
                            position = it.position,
                            comment = viewBinding.etCommentExercise.text.toString(),
                            deleted = it.deleted
                        )
                    )
                }
                viewModel.addNewExerciseAutofill(
                    exercisePattern(
                        nameExercise = viewBinding.autoCompleteTvexercisePattern.text.toString()
                    )
                )
            } else {
                Toast.makeText(
                    this.context,
                    getString(CommonRString.exercise_name_is_empty),
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
                    idExercise = params.exerciseId
                )
            )
        }
        params.let {
            viewBinding.autoCompleteTvexercisePattern.setText(it.name)
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
