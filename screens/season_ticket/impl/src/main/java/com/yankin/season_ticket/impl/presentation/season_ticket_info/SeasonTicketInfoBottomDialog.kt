package com.yankin.season_ticket.impl.presentation.season_ticket_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.trainingdiary.season_ticket.impl.R
import com.yankin.trainingdiary.season_ticket.impl.databinding.BottomSheetSeasonTicketInformationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class SeasonTicketInfoBottomDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetSeasonTicketInformationBinding

    private val viewModel: SeasonTicketInfoViewModel by viewModels()

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
            if (it > 99) {
                viewBinding.tvTrainingsAmount.text = "ထ"
            } else {
                viewBinding.tvTrainingsAmount.text = it.toString()
            }
        }
        viewModel.subscriptionEndDate.observe(this.viewLifecycleOwner) {
            if (it.equals("01.01.70")) {
                viewBinding.tvDateValid.text = "ထ"
            } else {
                viewBinding.tvDateValid.text = it.toString()
            }
        }
        viewBinding.btnReset.setOnClickListener {
            runBlocking {
                viewModel.resetSeasonTicket()
                delay(50)
                dismiss()
            }
        }
        viewBinding.tvDaysAmount.text = viewModel.daysAmount()
    }
}
