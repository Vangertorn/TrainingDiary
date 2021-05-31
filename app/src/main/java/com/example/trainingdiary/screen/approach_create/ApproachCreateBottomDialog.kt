package com.example.trainingdiary.screen.approach_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddApproachBinding
import com.example.trainingdiary.models.Approach
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ApproachCreateBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetAddApproachBinding

    private val viewModel: ApproachCreateViewModel by viewModel()

    private val adapter = ApproachRecyclerViewAdapter(onClick = { approach ->
        viewModel.deleteApproach(approach)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetAddApproachBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_approach, container, false)
        )
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.rvApproach.adapter = adapter
        viewModel.approachLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.btnAddApproach.setOnClickListener {
            viewModel.addNewApproach(
                Approach(
                    weight = viewBinding.etWeight.text.toString(),
                    reoccurrences = viewBinding.etReoccurrence.text.toString()
                )
            )
        }
    }


}