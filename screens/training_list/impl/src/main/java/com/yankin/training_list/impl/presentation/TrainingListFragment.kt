package com.yankin.training_list.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.debounce.debounceClick
import com.yankin.common.fragment.SupportFragmentInset
import com.yankin.common.fragment.VerticalInset
import com.yankin.common.recyclerview.SwipeCallback
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.view.setVerticalMargin
import com.yankin.exercise_list.api.navigation.ExerciseListCommunicator
import com.yankin.exercise_list.api.navigation.ExerciseListParams
import com.yankin.membership.api.navigation.MembershipCommunicator
import com.yankin.settings.api.navigation.SettingsCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateParams
import com.yankin.training_list.impl.presentation.adapter.TrainingRecyclerViewAdapter
import com.yankin.training_list.impl.presentation.models.TrainingListEvent
import com.yankin.trainingdiary.training_list.impl.R
import com.yankin.trainingdiary.training_list.impl.databinding.FragmentTrainingListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrainingListFragment : SupportFragmentInset<FragmentTrainingListBinding>(R.layout.fragment_training_list) {

    @Inject
    lateinit var trainingCreateCommunicator: TrainingCreateCommunicator

    @Inject
    lateinit var settingsCommunicator: SettingsCommunicator

    @Inject
    lateinit var membershipCommunicator: MembershipCommunicator

    @Inject
    lateinit var exerciseListCommunicator: ExerciseListCommunicator

    override lateinit var viewBinding: FragmentTrainingListBinding

    private val viewModel: TrainingListViewModel by viewModels()

    private val adapter = TrainingRecyclerViewAdapter(
        onClick = { training ->
            viewModel.rememberIdTraining(training)
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
    )

    private val simpleCallback = SwipeCallback { position, direction ->
        when (direction) {
            ItemTouchHelper.LEFT -> {
                deleteTraining(position)
            }

            ItemTouchHelper.RIGHT -> {
                deleteTraining(position)
            }
        }
    }
    private val dataObserverAsc = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            viewBinding.recyclerViewTraining.scrollToPosition(0)
        }
    }
    private val dataObserverDesc = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            viewBinding.recyclerViewTraining.scrollToPosition(adapter.itemCount - 1)
        }
    }
    private var dataObserverChek: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentTrainingListBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.fragment_training_list, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrainingListUiStream().observeWithLifecycle(this) { uiState ->
            viewBinding.tvNumberTraining.isVisible = uiState.trainingLeft.isNotEmpty()
            viewBinding.tvNumberTraining.text = uiState.trainingLeft

            viewBinding.tvNumberDays.isVisible = uiState.daysLeft.isNotEmpty()
            viewBinding.tvNumberDays.text = uiState.daysLeft
        }

        viewModel.getTrainingCreateEventStream().observeWithLifecycle(this) { event ->
            when(event){
                TrainingListEvent.Default -> {}
                TrainingListEvent.NavigateToCreateMembership -> {
                    membershipCommunicator.navigateToCreateMembership()
                    viewModel.onEventHandle()
                }
                is TrainingListEvent.NavigateToEditMembership -> {
                    membershipCommunicator.navigateToMembership(event.params)
                    viewModel.onEventHandle()
                }
            }
        }

//        viewModel.numberTrainingLiveData.observe(this.viewLifecycleOwner) {
//            if (it >= 0) {
//                viewBinding.tvNumberTraining.visibility = View.VISIBLE
//                if (it > 99) {
//                    viewBinding.tvNumberTraining.text = "ထ"
//                } else {
//                    viewBinding.tvNumberTraining.text = it.toString()
//                }
//            } else {
//                viewBinding.tvNumberTraining.visibility = View.GONE
//            }
//        }

//        viewModel.numberLeftDaysLiveData.observe(this.viewLifecycleOwner) {
//            if (it >= 0) {
//                viewBinding.tvNumberDays.visibility = View.VISIBLE
//                if (it >= 365) {
//                    viewBinding.tvNumberDays.text = "ထ"
//                } else {
//                    viewBinding.tvNumberDays.text = it.toString()
//                }
//            } else {
//                viewBinding.tvNumberDays.visibility = View.GONE
//            }
//        }

        viewBinding.recyclerViewTraining.adapter = adapter

        viewBinding.btnAdd.setOnClickListener {
            trainingCreateCommunicator.navigateTo(TrainingCreateParams.CreateTraining)
        }

        viewBinding.subscriptionTrainingList.debounceClick {
            viewModel.onMembershipClick()
//            if (viewModel.numberOfTrainingSessions() == -1 && viewModel.subscriptionEndDate()
//                    .isEmpty()
//            ) {
//                seasonTicketCommunicator.navigateToSeasonTicket()
//            } else {
//                seasonTicketCommunicator.navigateToSeasonTicketInfo()
//            }
        }
        viewBinding.settingsTrainingList.setOnClickListener {
            settingsCommunicator.navigateTo()
        }
        viewModel.switchOrderLiveData.observe(this.viewLifecycleOwner) { boolean ->
            if (boolean) {
                if (dataObserverChek == null) {
                    adapter.registerAdapterDataObserver(dataObserverAsc)
                    dataObserverChek = DATA_OBSERVER_ASC
                }

                viewModel.trainingAscLiveData.observe(this.viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            } else {
                if (dataObserverChek == null) {
                    adapter.registerAdapterDataObserver(dataObserverDesc)
                    dataObserverChek = DATA_OBSERVER_DESC
                }

                viewModel.trainingDescLiveData.observe(this.viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }
        }
        val trainingHelper = ItemTouchHelper(simpleCallback)
        trainingHelper.attachToRecyclerView(viewBinding.recyclerViewTraining)
    }

    override fun onDestroyView() {
        dataObserverChek = if (dataObserverChek == DATA_OBSERVER_ASC) {
            adapter.unregisterAdapterDataObserver(dataObserverAsc)
            null
        } else {
            adapter.unregisterAdapterDataObserver(dataObserverDesc)
            null
        }

        super.onDestroyView()
    }

    private fun deleteTraining(position: Int) {
        val training = viewModel.getTrainingFromPosition(position)!!
        viewModel.deletedTrainingTrue(training)

        Snackbar.make(
            viewBinding.recyclerViewTraining,
            getString(CommonRString.training_was_delete),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(CommonRString.undo)) {
                viewModel.deletedTrainingFalse(training)
            }.apply {
                this.view.translationY = -savedInsets.bottom.toFloat()
            }.show()
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        this.savedInsets = VerticalInset(top, bottom, hasKeyboard)
        viewBinding.toolbarTrainingList.setPadding(0, top, 0, 0)
        viewBinding.btnAdd.setVerticalMargin(0, bottom)
        viewBinding.recyclerViewTraining.setPadding(0, 0, 0, bottom)
    }

    companion object {
        private const val DATA_OBSERVER_ASC = 1
        private const val DATA_OBSERVER_DESC = 2
    }
}
