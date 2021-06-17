package com.example.trainingdiary.screen.season_ticket_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetSeasonTicketInformationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SeasonTicketInfoBottomDialog : BottomSheetDialogFragment() {


    private lateinit var viewBinding: BottomSheetSeasonTicketInformationBinding

    private val viewModel: SeasonTicketInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetSeasonTicketInformationBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_season_ticket_information, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.numberOfTrainingSessionLiveData.observe(this.viewLifecycleOwner) {
            viewBinding.tvTrainingsAmount.text = it.toString()
        }
        viewModel.subscriptionEndDate.observe(this.viewLifecycleOwner) {
            viewBinding.tvDateValid.text = it.toString()
        }
        viewBinding.btnReset.setOnClickListener {
            viewModel.resetSeasonTicket()
            findNavController().popBackStack()
        }

    }

}