package com.example.trainingdiary.screen.training_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentTrainingListBinding
import com.example.trainingdiary.screen.approach_create.ApproachCreateBottomDialog
import com.example.trainingdiary.support.SwipeCallback
import com.example.trainingdiary.support.navigateSave
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrainingListFragment : Fragment() {

    private lateinit var viewBinding: FragmentTrainingListBinding

    private val viewModel: TrainingListViewModel by viewModel()

    private val adapter = TrainingRecyclerViewAdapter(onClick = { training ->

    })

    private val simpleCallback = SwipeCallback { position, direction ->
        when (direction) {
            ItemTouchHelper.LEFT -> {
               viewModel.deleteTraining(position)
            }
            ItemTouchHelper.RIGHT -> {
                viewModel.deleteTraining(position)
            }
        }
    }

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
        viewBinding.recyclerViewTraining.adapter = adapter
        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigateSave(TrainingListFragmentDirections.actionTrainingListFragmentToTrainingCreateFragment())
        }
        viewModel.trainingLiveData.observe(this.viewLifecycleOwner){
            adapter.submitList(it)
        }
        val noteHelper = ItemTouchHelper(simpleCallback)
        noteHelper.attachToRecyclerView(viewBinding.recyclerViewTraining)

        viewBinding.settingsTrainingList.setOnClickListener {
            val bottomFragment = ApproachCreateBottomDialog()
            bottomFragment.show(childFragmentManager, "")
        }
    }
}