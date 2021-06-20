package com.example.trainingdiary.screen.season_ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetSeasonTicketBinding
import com.example.trainingdiary.support.CalendarView
import com.example.trainingdiary.support.navigateSave
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class SeasonTicketBottomDialog : BottomSheetDialogFragment() {


    private lateinit var viewBinding: BottomSheetSeasonTicketBinding

    private val viewModel: SeasonTicketViewModel by viewModel()
    private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    private var selectedDate: Date = Date()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetSeasonTicketBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_season_ticket, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.calendar.onDateChangedCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                selectedDate.time = date.time
            }
        }


        viewBinding.btnSaveSeasonTicket.setOnClickListener {
            var amount: Int? = null
            var date: Date? = null
            if (selectedDate >= Date()) {
                date = selectedDate
            } else {
                Toast.makeText(
                    this.context,
                    "Term in  season ticket cannot be less current date",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (viewBinding.etReoccurrence.text.toString().toInt() >= 1) {
                amount = viewBinding.etReoccurrence.text.toString().toInt()

            } else {
                Toast.makeText(
                    this.context,
                    "The number of trainings can not be less than 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (amount != null && date != null) {
                viewModel.saveNumberOfTrainingSessions(amount)
                viewModel.saveSubscriptionEndDate(dateFormatter.format(date))
                viewModel.saveDateCreatedTicket(dateFormatter.format(Date()))
                viewModel.saveDaysAmount(dateFormatter.format(date))
                this.dismiss()
                findNavController().navigateSave(SeasonTicketBottomDialogDirections.actionSeasonTicketBottomDialogToTrainingListFragment())
            }
        }

        if (viewBinding.etReoccurrence.text.toString().isBlank()) {
            viewBinding.etReoccurrence.setText("1")
        }
        viewBinding.ibAddQuantity.setOnClickListener {
            if (viewBinding.etReoccurrence.text.toString().isBlank()) {
                viewBinding.etReoccurrence.setText("1")
            } else {
                viewBinding.etReoccurrence.setText(
                    (viewBinding.etReoccurrence.text.toString().toInt() + 1).toString()
                )
            }

        }
        viewBinding.ibRemoveQuantity.setOnClickListener {
            if (viewBinding.etReoccurrence.text.toString().isBlank()) {
                viewBinding.etReoccurrence.setText("0")
            } else {
                if (viewBinding.etReoccurrence.text.toString().toDouble() > 0) {
                    viewBinding.etReoccurrence.setText(
                        (viewBinding.etReoccurrence.text.toString().toInt() - 1).toString()
                    )
                } else {
                    viewBinding.etReoccurrence.setText("0")
                }

            }
        }
    }


}