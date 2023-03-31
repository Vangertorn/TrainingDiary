package com.yankin.trainingdiary.screen.training_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentTrainingListBinding
import com.example.trainingdiary.support.navigateSave
import com.example.trainingdiary.support.setVerticalMargin
import com.google.android.material.snackbar.Snackbar
import com.yankin.trainingdiary.screen.training_list.adapter.TrainingRecyclerViewAdapter
import com.yankin.trainingdiary.support.SupportFragmentInset
import com.yankin.trainingdiary.support.SwipeCallback
import com.yankin.trainingdiary.support.VerticalInset
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainingListFragment :
    SupportFragmentInset<FragmentTrainingListBinding>(R.layout.fragment_training_list) {

    override lateinit var viewBinding: FragmentTrainingListBinding

    private val viewModel: TrainingListViewModel by viewModels()

    private val adapter = TrainingRecyclerViewAdapter(
        onClick = { training ->
            viewModel.rememberIdTraining(training)
            findNavController().navigateSave(
                TrainingListFragmentDirections.actionTrainingListFragmentToExerciseListFragment(
                    training
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

        viewModel.numberTrainingLiveData.observe(this.viewLifecycleOwner) {
            if (it >= 0) {
                viewBinding.tvNumberTraining.visibility = View.VISIBLE
                if (it > 99) {
                    viewBinding.tvNumberTraining.text = "ထ"
                } else {
                    viewBinding.tvNumberTraining.text = it.toString()
                }
            } else {
                viewBinding.tvNumberTraining.visibility = View.GONE
            }
        }

        viewModel.numberLeftDaysLiveData.observe(this.viewLifecycleOwner) {
            if (it >= 0) {
                viewBinding.tvNumberDays.visibility = View.VISIBLE
                if (it >= 365) {
                    viewBinding.tvNumberDays.text = "ထ"
                } else {
                    viewBinding.tvNumberDays.text = it.toString()
                }
            } else {
                viewBinding.tvNumberDays.visibility = View.GONE
            }
        }

        viewBinding.recyclerViewTraining.adapter = adapter

        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigateSave(
                TrainingListFragmentDirections.actionTrainingListFragmentToTrainingCreateBottomDialog(

                )
            )
        }

        viewBinding.subscriptionTrainingList.setOnClickListener {
            if (viewModel.numberOfTrainingSessions() == -1 && viewModel.subscriptionEndDate()
                .isEmpty()
            ) {
                findNavController().navigateSave(TrainingListFragmentDirections.actionTrainingListFragmentToSeasonTicketBottomDialog())
            } else {
                findNavController().navigateSave(TrainingListFragmentDirections.actionTrainingListFragmentToSeasonTicketInfoBottomDialog())
            }
        }
        viewBinding.settingsTrainingList.setOnClickListener {
            findNavController().navigateSave(TrainingListFragmentDirections.actionTrainingListFragmentToSettingsFragment())
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
            getString(R.string.training_was_delete),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.undo)) {
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
