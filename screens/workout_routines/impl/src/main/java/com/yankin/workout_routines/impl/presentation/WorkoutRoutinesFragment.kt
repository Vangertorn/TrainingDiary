package com.yankin.workout_routines.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.yankin.approach_create.api.navigation.SetCreateCommunicator
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.fragment.BaseFragment
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedParamsViewModelFactory
import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.api.navigation.ExerciseCreateParams
import com.yankin.kotlin.unsafeLazy
import com.yankin.navigation.BundleParcelable
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateParams
import com.yankin.trainingdiary.exercise_list.impl.R
import com.yankin.trainingdiary.exercise_list.impl.databinding.FragmentWorkoutRoutinesBinding
import com.yankin.workout_routines.api.navigation.WorkoutRoutinesCommunicator
import com.yankin.workout_routines.impl.di.WorkoutRoutinesViewModelFactory
import com.yankin.workout_routines.impl.navigation.WorkoutRoutinesParcelableParams
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.WorkoutRoutinesAdapter
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.WorkoutRoutinesSimpleCallback
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class WorkoutRoutinesFragment :
    BaseFragment<FragmentWorkoutRoutinesBinding>(R.layout.fragment_workout_routines) {

    @Inject
    lateinit var exerciseCreateCommunicator: ExerciseCreateCommunicator

    @Inject
    lateinit var trainingCreateCommunicator: TrainingCreateCommunicator

    @Inject
    lateinit var setCreateCommunicator: SetCreateCommunicator

    @Inject
    lateinit var viewModelFactory: WorkoutRoutinesViewModelFactory

    override val binding: FragmentWorkoutRoutinesBinding by viewBinding(FragmentWorkoutRoutinesBinding::bind)
    private val viewModel: WorkoutRoutinesViewModel by viewModels {
        AssistedParamsViewModelFactory(factory = viewModelFactory, params = params, owner = this)
    }
    private val params by BundleParcelable<WorkoutRoutinesParcelableParams>(WorkoutRoutinesCommunicator.NAV_KEY)
    private val adapter by unsafeLazy {
        WorkoutRoutinesAdapter(
            dragListener = { viewHolder ->
                touchHelper?.startDrag(viewHolder)
            },
            onTrainingBlockClick = viewModel::onClickTrainingBlock
        )
    }
    private val swipeCallback by unsafeLazy {
        WorkoutRoutinesSimpleCallback(
            recyclerView = binding.recyclerView,
            onSwipe = viewModel::onSwipeTrainingBlock,
            onSwitch = viewModel::onTrainingBlockPositionSwitch
        )
    }
    private var touchHelper: ItemTouchHelper? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        touchHelper = ItemTouchHelper(swipeCallback)
        touchHelper?.attachToRecyclerView(binding.recyclerView)

        binding.toolbarExerciseList.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAdd.setOnClickListener {
            exerciseCreateCommunicator.navigateToExerciseCreate(ExerciseCreateParams(params.trainingId))
        }
        binding.ivEditTrainingExerciseList.setOnClickListener {
            trainingCreateCommunicator.navigateTo(TrainingCreateParams.EditTraining(trainingId = params.trainingId))
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)

        binding.recyclerView.adapter = adapter
    }

    override fun onObserveData() {
        viewModel.getUiStream().observeWithLifecycle(this) { uiState ->
            adapter.items = uiState.exercises
            binding.dateTrainingExerciseList.text = uiState.trainingDate
            binding.weightExerciseList.text = uiState.trainingWeight
            binding.muscleGroupsExerciseList.text = uiState.trainingMuscleGroups
            binding.commentExerciseList.text = uiState.trainingComment
        }
        viewModel.getEventStream().observeWithLifecycle(this) { event ->
            when (event) {
                WorkoutRoutinesEvent.Default -> {}
                is WorkoutRoutinesEvent.NavigateToSetCreate -> {
                    setCreateCommunicator.navigateToSetCreate(event.params)
                    viewModel.onEventHandle()
                }
                is WorkoutRoutinesEvent.ShowSnackBar -> {
                    Snackbar.make(
                        binding.recyclerView,
                        event.message,
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(event.actionName) {
                            viewModel.onUndoClick(event.trainingBlockId)
                        }.apply {
                            this.view.translationY = -savedInsets.bottom.toFloat()
                        }.show()
                    viewModel.onEventHandle()
                }
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        binding.toolbarExerciseList.setPadding(0, top, 0, 0)
    }
}