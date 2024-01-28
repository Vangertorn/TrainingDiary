package com.yankin.training_create.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yankin.common.custom_view.CalendarView
import com.yankin.common.dialog.BaseBottomSheetDialogFragment
import com.yankin.common.hideKeyboard
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.viewbinding.viewBinding
import com.yankin.exercise_list.api.navigation.ExerciseListCommunicator
import com.yankin.exercise_list.api.navigation.ExerciseListParams
import com.yankin.navigation.BundleParcelable
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.impl.navigation.TrainingCreateParcelableParams
import com.yankin.trainingdiary.training_create.impl.R
import com.yankin.trainingdiary.training_create.impl.databinding.BottomSheetAddTrainingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class TrainingCreateBottomDialog : BaseBottomSheetDialogFragment<BottomSheetAddTrainingBinding>() {

    @Inject
    lateinit var exerciseListCommunicator: ExerciseListCommunicator

    private val viewModel: TrainingCreateViewModel by viewModels()
    private var selectedDate: Date = Date()
    private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    private val params by BundleParcelable<TrainingCreateParcelableParams>(TrainingCreateCommunicator.NAV_KEY)
    private val adapter = MuscleGroupsRecyclerViewAdapter(
        onClick = {
        }
    )

    override fun parentLayoutId(): Int = R.id.addTrainingDialog

    override fun attrColorBackground(): Int = CommonRAttr.contentBackground

    override val binding: BottomSheetAddTrainingBinding by viewBinding(BottomSheetAddTrainingBinding::inflate)

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
        binding.rvMuscleCroups.adapter = adapter
        //        viewBinding.rvMuscleCroups.layoutManager = chipsLayoutManager
        viewModel.muscleGroupLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it.map { it.toModel() })
        }
        binding.calendar.onDateChangedCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                selectedDate.time = date.time
            }
        }

        params.training?.let {
            binding.etCommentCreateTraining.setText(it.comment)
            binding.etWeightCreateTraining.setText(it.weight)
            adapter.selectedPositions = it.selectedMuscleGroup
            binding.calendar.selectedDate = dateFormatter.parse(it.date)
            selectedDate = dateFormatter.parse(it.date)!!
        }

        binding.confirmCreateTraining.setOnClickListener {

            if (params.training == null) {
                val training = Training(
                    date = dateFormatter.format(selectedDate),
                    comment = binding.etCommentCreateTraining.text.toString(),
                    weight = binding.etWeightCreateTraining.text.toString(),
                    muscleGroups = viewModel.addMuscleGroups(adapter.selectedPositions),
                    selectedMuscleGroup = adapter.selectedPositions
                )
                viewModel.addNewTraining(training)
                viewModel.takeAwayTraining(training)
                hideKeyboard()
                findNavController().popBackStack()
            } else {
                params.training?.let {
                    val training = Training(
                        id = it.id,
                        date = dateFormatter.format(selectedDate),
                        comment = binding.etCommentCreateTraining.text.toString(),
                        weight = binding.etWeightCreateTraining.text.toString(),
                        muscleGroups = viewModel.addMuscleGroups(adapter.selectedPositions),
                        selectedMuscleGroup = adapter.selectedPositions
                    )
                    viewModel.updateTraining(training)
                    hideKeyboard()
                    exerciseListCommunicator.navigateTo(
                        params = ExerciseListParams(
                            trainingId = training.id,
                            date = training.date,
                            muscleGroups = training.muscleGroups,
                            comment = training.comment,
                            weight = training.weight,
                            position = training.position,
                            deleted = training.deleted,
                            selectedMuscleGroup = training.selectedMuscleGroup,
                        )
                    )
                }
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }
}