package com.example.trainingdiary.screen.training_create


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddTrainingBinding
import java.util.*
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.support.CalendarView
import com.example.trainingdiary.support.extensions.hideKeyboard
import com.example.trainingdiary.support.navigateSave
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class TrainingCreateBottomDialog : BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetAddTrainingBinding
    private val viewModel: TrainingCreateViewModel by viewModel()
    private var selectedDate: Date = Date()
    private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    private val args: TrainingCreateBottomDialogArgs by navArgs()
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
        val chipsLayoutManager =
            ChipsLayoutManager.newBuilder(requireContext())
                .setChildGravity(Gravity.TOP)
                .setScrollingEnabled(false)
                .setGravityResolver { Gravity.CENTER }
                .setRowBreaker { false }
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER)
                .withLastRow(true)
                .build()
        viewBinding.rvMuscleCroups.adapter = adapter
        viewBinding.rvMuscleCroups.layoutManager = chipsLayoutManager
        viewModel.muscleGroupLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewBinding.calendar.onDateChangedCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                selectedDate.time = date.time
            }
        }

        args.training?.let {
            viewBinding.etCommentCreateTraining.setText(it.comment)
            viewBinding.etWeightCreateTraining.setText(it.weight)
            adapter.selectedPositions = it.selectedMuscleGroup
            viewBinding.calendar.selectedDate = dateFormatter.parse(it.date)
            selectedDate = dateFormatter.parse(it.date)!!
        }

        viewBinding.confirmCreateTraining.setOnClickListener {

            if (args.training == null) {
                val training = Training(
                    date = dateFormatter.format(selectedDate),
                    comment = viewBinding.etCommentCreateTraining.text.toString(),
                    weight = viewBinding.etWeightCreateTraining.text.toString(),
                    muscleGroups = viewModel.addMuscleGroups(adapter.selectedPositions),
                    selectedMuscleGroup = adapter.selectedPositions
                )
                viewModel.addNewTraining(training)
                viewModel.takeAwayTraining(training)
                hideKeyboard()
                findNavController().popBackStack()
            } else {
                args.training?.let {
                    val training = Training(
                        id = it.id,
                        date = dateFormatter.format(selectedDate),
                        comment = viewBinding.etCommentCreateTraining.text.toString(),
                        weight = viewBinding.etWeightCreateTraining.text.toString(),
                        muscleGroups = viewModel.addMuscleGroups(adapter.selectedPositions),
                        selectedMuscleGroup = adapter.selectedPositions
                    )
                    viewModel.updateTraining(training)
                    hideKeyboard()
                    findNavController().navigateSave(
                        TrainingCreateBottomDialogDirections.actionTrainingCreateBottomDialogToExerciseListFragment(
                            training
                        )
                    )
                }
            }
        }

    }


}