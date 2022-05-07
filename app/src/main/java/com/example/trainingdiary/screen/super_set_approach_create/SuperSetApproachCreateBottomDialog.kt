package com.example.trainingdiary.screen.super_set_approach_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddApproachSuperSetBinding
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.info.ViewHolderTypes
import com.example.trainingdiary.screen.approach_create.ApproachRecyclerViewAdapter
import com.example.trainingdiary.support.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class SuperSetApproachCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetAddApproachSuperSetBinding

    private val viewModel: SuperSetApproachCreateViewModel by viewModel()

    private lateinit var selectedExercise: ViewHolderTypes.ExerciseInfo

    private val adapterApproach = ApproachRecyclerViewAdapter(onClick = { approach ->
        viewModel.deleteApproach(approach)
    })
    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            viewBinding.rvApproachSs.scrollToPosition(adapterApproach.itemCount - 1)
        }
    }

    private val adapter = SuperSetExerciseInfoRecyclerViewAdapter(onClick = { exerciseInfo ->
        selectedExercise = exerciseInfo
        viewModel.rememberIdExercise(exerciseInfo.exercise)
        viewBinding.autoCompleteTvExerciseNameSs.setText(exerciseInfo.exercise.name)
        viewBinding.etCommentExerciseSs.setText(exerciseInfo.exercise.comment)

    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetAddApproachSuperSetBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_approach_super_set, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedExercise = viewModel.exerciseInfoFirst()
        viewBinding.autoCompleteTvExerciseNameSs.setText(selectedExercise.exercise.name)
        viewBinding.etCommentExerciseSs.setText(selectedExercise.exercise.comment)
        viewModel.rememberIdExercise(selectedExercise.exercise)


        viewBinding.rvExerciseInSuperSetAddApproach.adapter = adapter
        viewModel.exerciseInfoLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }


        viewBinding.rvApproachSs.adapter = adapterApproach
        viewModel.approachLiveData.observe(this.viewLifecycleOwner) {
            adapterApproach.submitList(it)
            if (it.isNotEmpty()) {
                viewBinding.etWeightSs.setText(it[it.size - 1].weight)
                viewBinding.etReoccurrenceSs.setText(it[it.size - 1].reoccurrences)
            }
        }
        adapterApproach.registerAdapterDataObserver(dataObserver)

        viewBinding.btnAddApproachSs.setOnClickListener {
            viewBinding.etWeightSs.chekDoubleEmpty()
            viewBinding.etReoccurrenceSs.chekIntEmpty()

            viewModel.addNewApproach(
                Approach(
                    weight = viewBinding.etWeightSs.text.toString(),
                    reoccurrences = viewBinding.etReoccurrenceSs.text.toString(),
                    idExercise = selectedExercise.exercise.id
                )
            )
        }












        viewModel.reoccurrencesLiveData.observe(this.viewLifecycleOwner) {
            if (viewBinding.etReoccurrenceSs.text.isBlank()) {
                viewBinding.etReoccurrenceSs.setText(it)
            }
        }

        viewModel.weightLiveData.observe(this.viewLifecycleOwner) {
            if (viewBinding.etWeightSs.text.isBlank()) {
                viewBinding.etWeightSs.setText(it)
            }
        }

        viewBinding.ibAddQuantitySs.setOnClickListener {
            viewBinding.etReoccurrenceSs.addInt(1)
        }
        viewBinding.ibRemoveQuantitySs.setOnClickListener {
            viewBinding.etReoccurrenceSs.removeInt(1)
        }

        viewBinding.ibAddWeightSs.setOnClickListener {
            viewBinding.etWeightSs.addDouble(1.0)
        }
        viewBinding.ibRemoveWeightSs.setOnClickListener {
            viewBinding.etWeightSs.removeDouble(1.0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapterApproach.unregisterAdapterDataObserver(dataObserver)
    }

}