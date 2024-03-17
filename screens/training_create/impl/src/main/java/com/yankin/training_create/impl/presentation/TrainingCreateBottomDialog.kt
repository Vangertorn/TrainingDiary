package com.yankin.training_create.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.custom_view.CalendarView
import com.yankin.common.dialog.BaseBottomSheetDialogFragment
import com.yankin.common.hideKeyboard
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedParamsViewModelFactory
import com.yankin.workout_routines.api.navigation.WorkoutRoutinesCommunicator
import com.yankin.kotlin.unsafeLazy
import com.yankin.navigation.BundleParcelable
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.impl.di.TrainingCreateViewModelFactory
import com.yankin.training_create.impl.navigation.TrainingCreateParcelableParams
import com.yankin.training_create.impl.presentation.adapter.MuscleGroupsAdapter
import com.yankin.training_create.impl.presentation.models.TrainingCreateEvent
import com.yankin.trainingdiary.training_create.impl.R
import com.yankin.trainingdiary.training_create.impl.databinding.BottomSheetAddTrainingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
internal class TrainingCreateBottomDialog : BaseBottomSheetDialogFragment<BottomSheetAddTrainingBinding>() {

    @Inject
    lateinit var viewModelFactory: TrainingCreateViewModelFactory

    @Inject
    lateinit var workoutRoutinesCommunicator: WorkoutRoutinesCommunicator

    override fun parentLayoutId(): Int = R.id.addTrainingDialog
    override fun attrColorBackground(): Int = CommonRAttr.contentBackground
    override val binding: BottomSheetAddTrainingBinding by viewBinding(BottomSheetAddTrainingBinding::inflate)
    private val viewModel: TrainingCreateViewModel by viewModels {
        AssistedParamsViewModelFactory(params = params, factory = viewModelFactory, owner = this)
    }
    private val params by BundleParcelable<TrainingCreateParcelableParams>(TrainingCreateCommunicator.NAV_KEY)
    private val adapter by unsafeLazy {
        MuscleGroupsAdapter(
            muscleGroupClickListener = viewModel::onMuscleGroupClick
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etComment.doOnTextChanged{text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onCommentChange(text ?: "")
            }
        }
        binding.etWeight.doOnTextChanged{text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onWeightChange(text ?: "")
            }
        }

        binding.butConfirm.setOnClickListener {
            viewModel.onSaveClick()
            hideKeyboard()
        }
    }

    override fun onInitView() {
        binding.rvMuscleCroups.adapter = adapter
        binding.vCalendar.onDateChangedCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                viewModel.onDateChange(date)
            }
        }
    }

    override fun onObserveData() {
        viewModel.getTrainingCreateUiStream().observeWithLifecycle(this) { uiState ->
            adapter.items = uiState.muscleGroupList
            if (uiState.weight != binding.etWeight.text?.toString()) {
                binding.etWeight.setText(uiState.weight)
            }
            if (uiState.comment != binding.etComment.text?.toString()) {
                binding.etComment.setText(uiState.comment)
            }
            binding.vCalendar.selectedDate = uiState.selectedDate
        }
        viewModel.getTrainingCreateEventStream().observeWithLifecycle(this) { event ->
            when(event){
                TrainingCreateEvent.Back -> {
                    findNavController().popBackStack()
                    viewModel.onEventHandle()
                }
                TrainingCreateEvent.Default -> {}
                is TrainingCreateEvent.NavigateToExerciseList -> {
                    workoutRoutinesCommunicator.navigateTo(event.params)
                    viewModel.onEventHandle()
                }
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }
}