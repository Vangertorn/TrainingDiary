package com.yankin.training_exercises.impl.presentation

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
import com.yankin.training_exercises.api.navigation.TrainingExercisesCommunicator
import com.yankin.training_exercises.impl.di.TrainingExercisesViewModelFactory
import com.yankin.training_exercises.impl.navigation.TrainingExercisesParcelableParams
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.TrainingExercisesAdapter
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.TrainingExercisesSwipeCallback
import com.yankin.training_exercises.impl.presentation.models.TrainingExercisesEvent
import com.yankin.trainingdiary.exercise_list.impl.R
import com.yankin.trainingdiary.exercise_list.impl.databinding.FragmentTrainingExercisesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
internal class TrainingExercisesFragment :
    BaseFragment<FragmentTrainingExercisesBinding>(R.layout.fragment_training_exercises) {

    @Inject
    lateinit var exerciseCreateCommunicator: ExerciseCreateCommunicator

    @Inject
    lateinit var trainingCreateCommunicator: TrainingCreateCommunicator

    @Inject
    lateinit var setCreateCommunicator: SetCreateCommunicator

    @Inject
    lateinit var viewModelFactory: TrainingExercisesViewModelFactory

    override val binding: FragmentTrainingExercisesBinding by viewBinding(FragmentTrainingExercisesBinding::bind)
    private val viewModel: TrainingExercisesViewModel by viewModels {
        AssistedParamsViewModelFactory(factory = viewModelFactory, params = params, owner = this)
    }
    private val params by BundleParcelable<TrainingExercisesParcelableParams>(TrainingExercisesCommunicator.NAV_KEY)
    private val adapter by unsafeLazy {
        TrainingExercisesAdapter(onTrainingExercisesClick = viewModel::onClickTrainingExercise)
    }
    private val swipeCallback  by unsafeLazy {
        TrainingExercisesSwipeCallback(viewModel::onSwipeTrainingExercise)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exerciseHelper = ItemTouchHelper(swipeCallback)
        exerciseHelper.attachToRecyclerView(binding.recyclerView)

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

        binding.recyclerView.adapter = adapter.apply {
            setHasStableIds(true)
        }
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
                TrainingExercisesEvent.Default -> {}
                is TrainingExercisesEvent.NavigateToSetCreate -> {
                    setCreateCommunicator.navigateToSetCreate(event.params)
                    viewModel.onEventHandle()
                }
                is TrainingExercisesEvent.ShowSnackBar -> {
                    Snackbar.make(
                        binding.recyclerView,
                        event.message,
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(event.actionName) {
                            viewModel.onUndoClick(event.trainingExerciseId)
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