package com.example.trainingdiary.screen.training_create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddTrainingBinding
import java.util.*
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.support.CalendarView
import com.example.trainingdiary.support.hideKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class TrainingCreateBottomDialog : BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetAddTrainingBinding
    private val viewModel: TrainingCreateViewModel by viewModel()
    private var selectedDate: Date = Date()
    private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    private val args: TrainingCreateBottomDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetAddTrainingBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_add_training, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.muscleGroupLiveData.observe(this.viewLifecycleOwner) {
            for (muscleGroup in it) {
                addNewChip(muscleGroup.nameMuscleGroup)
            }
        }
        viewBinding.calendar.onDateChangedCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                selectedDate.time = date.time
            }
        }
        args.training?.let {
            viewBinding.etCommentCreateTraining.setText(it.comment)
            viewBinding.etWeightCreateTraining.setText(it.weight)
        }

        viewBinding.confirmCreateTraining.setOnClickListener {
            viewModel.addNewTraining(
                Training(
                    date = dateFormatter.format(selectedDate),
                    comment = viewBinding.etCommentCreateTraining.text.toString(),
                    weight = viewBinding.etWeightCreateTraining.text.toString()
                )
            )
            hideKeyboard()
            findNavController().popBackStack()
        }

    }

    private fun addNewChip(muscleGroup: String) {
        val chip = Chip(this.context)
        chip.apply {
            text = muscleGroup
            isChipIconVisible = false
            isClickable = true
            isCheckable = true
            isCloseIconVisible = false
            viewBinding.apply {
                cgMuscleGroup.addView(chip as View)
//                chip.setOnCloseIconClickListener {
//                    cgMuscleGroup.removeView(chip as View)
//                }
            }
        }
    }

    private fun choiceChips() {
        viewBinding.cgMuscleGroup.setOnCheckedChangeListener { group, checkedId ->

            val chip: Chip? = group.findViewById(checkedId)

            chip?.let {
                Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
            }


        }

    }
}