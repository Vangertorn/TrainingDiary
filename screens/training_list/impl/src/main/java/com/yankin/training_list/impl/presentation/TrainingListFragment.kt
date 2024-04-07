package com.yankin.training_list.impl.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.debounce.debounceClick
import com.yankin.common.fragment.BaseFragment
import com.yankin.common.fragment.VerticalInset
import com.yankin.common.fragment.onBackPressed
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.vibrate.VibrateUtil
import com.yankin.common.view.setVerticalMargin
import com.yankin.common.viewbinding.viewBinding
import com.yankin.kotlin.unsafeLazy
import com.yankin.membership.api.navigation.MembershipCommunicator
import com.yankin.settings.api.navigation.SettingsCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateParams
import com.yankin.training_list.impl.presentation.adapter.TrainingsAdapter
import com.yankin.training_list.impl.presentation.adapter.TrainingsSwipeCallback
import com.yankin.training_list.impl.presentation.models.TrainingListEvent
import com.yankin.trainingdiary.training_list.impl.R
import com.yankin.trainingdiary.training_list.impl.databinding.FragmentTrainingListBinding
import com.yankin.workout_routines.api.navigation.WorkoutRoutinesCommunicator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrainingListFragment : BaseFragment<FragmentTrainingListBinding>(R.layout.fragment_training_list) {

    @Inject
    lateinit var trainingCreateCommunicator: TrainingCreateCommunicator

    @Inject
    lateinit var settingsCommunicator: SettingsCommunicator

    @Inject
    lateinit var membershipCommunicator: MembershipCommunicator

    @Inject
    lateinit var workoutRoutinesCommunicator: WorkoutRoutinesCommunicator

    override val binding: FragmentTrainingListBinding by viewBinding(FragmentTrainingListBinding::bind)

    private val viewModel: TrainingListViewModel by viewModels()

    private val adapter by unsafeLazy {
        TrainingsAdapter(onTrainingClick = viewModel::onTrainingClick)
    }
    private val swipeCallback by unsafeLazy {
        TrainingsSwipeCallback(
            recyclerView = binding.recyclerViewTraining,
            onSwipe = viewModel::onSwipeTraining,
        )
    }
    private var backPressTime: Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.debounceClick {
            trainingCreateCommunicator.navigateTo(TrainingCreateParams.CreateTraining)
        }

        binding.subscriptionTrainingList.debounceClick {
            viewModel.onMembershipClick()
        }
        binding.settingsTrainingList.debounceClick {
            settingsCommunicator.navigateTo()
        }
        val touchHelper = ItemTouchHelper(swipeCallback)
        touchHelper.attachToRecyclerView(binding.recyclerViewTraining)


        onBackPressed {
            onBackClick()
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)

        binding.recyclerViewTraining.adapter = adapter
    }

    override fun onObserveData() {
        viewModel.getTrainingListUiStream().observeWithLifecycle(this) { uiState ->
            binding.tvNumberTraining.isVisible = uiState.trainingLeft.isNotEmpty()
            binding.tvNumberTraining.text = uiState.trainingLeft

            binding.tvNumberDays.isVisible = uiState.daysLeft.isNotEmpty()
            binding.tvNumberDays.text = uiState.daysLeft
            adapter.items = uiState.trainings
            if (uiState.scrollToUp) binding.recyclerViewTraining.scrollToPosition(0)
            if (uiState.scrollToBottom) binding.recyclerViewTraining.scrollToPosition(uiState.trainings.size - 1)
        }

        viewModel.getTrainingCreateEventStream().observeWithLifecycle(this) { event ->
            when (event) {
                TrainingListEvent.Default -> {}
                TrainingListEvent.NavigateToCreateMembership -> {
                    membershipCommunicator.navigateToCreateMembership()
                    viewModel.onEventHandle()
                }
                is TrainingListEvent.NavigateToEditMembership -> {
                    membershipCommunicator.navigateToMembership(event.params)
                    viewModel.onEventHandle()
                }
                is TrainingListEvent.NavigateToWorkout -> {
                    workoutRoutinesCommunicator.navigateTo(event.params)
                    viewModel.onEventHandle()
                }
                is TrainingListEvent.ShowSnackBar -> event.handleEvent()
            }
        }
    }

    private fun TrainingListEvent.ShowSnackBar.handleEvent() {
        Snackbar.make(
            binding.recyclerViewTraining,
            message,
            Snackbar.LENGTH_LONG
        )
            .setAction(actionName) {
                viewModel.onUndoClick(trainingId)
            }.apply {
                this.view.translationY = -savedInsets.bottom.toFloat()
            }.show()
        viewModel.onEventHandle()
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        this.savedInsets = VerticalInset(top, bottom, hasKeyboard)
        binding.toolbarTrainingList.setPadding(0, top, 0, 0)
        binding.btnAdd.setVerticalMargin(0, bottom)
        binding.recyclerViewTraining.setPadding(0, 0, 0, bottom)
    }

    private fun onBackClick() {
        val currentTime = System.currentTimeMillis()
        backPressTime?.let { backPressTime ->
            if (currentTime - backPressTime < DOUBLE_CLICK_WAIT_TIME) {
                activity?.finishAffinity()
                return
            }
        }
        backPressTime = currentTime
        VibrateUtil(requireContext()).vibrate(VIBRATE_DURATION)
        if (activity?.isFinishing != true) {
            Toast.makeText(requireContext(), getString(CommonRString.press_back), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val DOUBLE_CLICK_WAIT_TIME = 2000
        private const val VIBRATE_DURATION = 100L
    }
}
