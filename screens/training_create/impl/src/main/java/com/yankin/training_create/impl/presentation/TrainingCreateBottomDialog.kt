package com.yankin.training_create.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.common.custom_view.CalendarView
import com.yankin.common.hideKeyboard
import com.yankin.navigation.BundleParcelable
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.impl.navigation.TrainingCreateParcelableParams
import com.yankin.trainingdiary.training_crate.impl.R
import com.yankin.trainingdiary.training_crate.impl.databinding.BottomSheetAddTrainingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TrainingCreateBottomDialog : BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetAddTrainingBinding
    private val viewModel: TrainingCreateViewModel by viewModels()
    private var selectedDate: Date = Date()
    private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    private val params by BundleParcelable<TrainingCreateParcelableParams>(TrainingCreateCommunicator.NAV_KEY)
    private val adapter = MuscleGroupsRecyclerViewAdapter(
        onClick = {
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetAddTrainingBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_training, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO не получилось обновить либу без мавен репозитория

//        val chipsLayoutManager =
//            ChipsLayoutManager.newBuilder(requireContext())
//                .setChildGravity(Gravity.TOP)
//                .setScrollingEnabled(false)
//                .setGravityResolver { Gravity.CENTER }
//                .setRowBreaker { false }
//                .setOrientation(ChipsLayoutManager.HORIZONTAL)
//                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER)
//                .withLastRow(true)
//                .build()
        viewBinding.rvMuscleCroups.adapter = adapter
//        viewBinding.rvMuscleCroups.layoutManager = chipsLayoutManager
        viewModel.muscleGroupLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it.map { it.toModel() })
        }
        viewBinding.calendar.onDateChangedCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                selectedDate.time = date.time
            }
        }

//        params.training?.let {
//            viewBinding.etCommentCreateTraining.setText(it.comment)
//            viewBinding.etWeightCreateTraining.setText(it.weight)
//            adapter.selectedPositions = it.selectedMuscleGroup
//            viewBinding.calendar.selectedDate = dateFormatter.parse(it.date)
//            selectedDate = dateFormatter.parse(it.date)!!
//        }

//        viewBinding.confirmCreateTraining.setOnClickListener {
//
//            if (params.training == null) {
//                val training = Training(
//                    date = dateFormatter.format(selectedDate),
//                    comment = viewBinding.etCommentCreateTraining.text.toString(),
//                    weight = viewBinding.etWeightCreateTraining.text.toString(),
//                    muscleGroups = viewModel.addMuscleGroups(adapter.selectedPositions),
//                    selectedMuscleGroup = adapter.selectedPositions
//                )
//                viewModel.addNewTraining(training)
//                viewModel.takeAwayTraining(training)
//                hideKeyboard()
//                findNavController().popBackStack()
//            } else {
//                params.training?.let {
//                    val training = Training(
//                        id = it.id,
//                        date = dateFormatter.format(selectedDate),
//                        comment = viewBinding.etCommentCreateTraining.text.toString(),
//                        weight = viewBinding.etWeightCreateTraining.text.toString(),
//                        muscleGroups = viewModel.addMuscleGroups(adapter.selectedPositions),
//                        selectedMuscleGroup = adapter.selectedPositions
//                    )
//                    viewModel.updateTraining(training)
//                    hideKeyboard()
////                    findNavController().navigateSave(
////                        TrainingCreateBottomDialogDirections.actionTrainingCreateBottomDialogToExerciseListFragment(
////                            training
////                        )
////                    )
//                }
//            }
//        }
    }
}