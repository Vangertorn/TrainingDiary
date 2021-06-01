package com.example.trainingdiary.screen.training_create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.BottomSheetAddTrainingBinding
import java.util.*
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.support.hideKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class TrainingCreateBottomDialog : BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetAddTrainingBinding
    private val viewModel: TrainingCreateViewModel by viewModel()
    private var noteDate = Date()
    private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

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
        viewBinding.confirmCreateTraining.setOnClickListener {
            viewModel.addNewTraining(
                Training(
                    date = dateFormatter.format(noteDate),
                    comment = viewBinding.etCommentCreateTraining.text.toString(),
                    weight = viewBinding.etWeightCreateTraining.text.toString()
                )
            )
            hideKeyboard()
            findNavController().popBackStack()
        }

    }
}