package com.yankin.exercise_create.impl.presentation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.debounce.debounceClick
import com.yankin.common.dialog.BaseBottomSheetDialogFragment
import com.yankin.common.hideKeyboard
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedParamsViewModelFactory
import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.impl.di.ExerciseCreateViewModelFactory
import com.yankin.exercise_create.impl.navigation.ExerciseCreateParcelableParams
import com.yankin.exercise_create.impl.presentation.adapter.ExerciseAdapter
import com.yankin.exercise_create.impl.presentation.models.ExerciseCreateEvent
import com.yankin.kotlin.unsafeLazy
import com.yankin.navigation.BundleParcelable
import com.yankin.trainingdiary.exercise_create.impl.R
import com.yankin.trainingdiary.exercise_create.impl.databinding.BottomSheetAddExerciseBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class ExerciseCreateBottomDialog : BaseBottomSheetDialogFragment<BottomSheetAddExerciseBinding>() {

    @Inject
    lateinit var viewModelFactory: ExerciseCreateViewModelFactory

    override fun parentLayoutId(): Int = R.id.addExerciseDialog
    override fun attrColorBackground(): Int = CommonRAttr.contentBackground
    override val binding: BottomSheetAddExerciseBinding by viewBinding(BottomSheetAddExerciseBinding::inflate)

    private val params by BundleParcelable<ExerciseCreateParcelableParams>(ExerciseCreateCommunicator.NAV_KEY)
    private val viewModel: ExerciseCreateViewModel by viewModels {
        AssistedParamsViewModelFactory(factory = viewModelFactory, params = params, owner = this)
    }
    private val adapter by unsafeLazy {
        ExerciseAdapter(
            exerciseClickListener = viewModel::onExerciseClick,
            addExerciseClickListener = viewModel::addExerciseClick
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAction.debounceClick {
            viewModel.onActionButtonClick()
        }
        binding.btnSave.debounceClick {
            viewModel.onSaveButtonClick()
            hideKeyboard()
        }
        binding.etExerciseName.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onExerciseNameChange(text ?: "")
            }
        }
        binding.etExerciseComment.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onCommentChange(text)
            }
        }
    }

    override fun onInitView() {
        binding.rvSuperSet.adapter = adapter
    }

    override fun onObserveData() {
        viewModel.getExerciseCreateUiStream().observeWithLifecycle(this) { uiState ->
            if (uiState.exerciseName != binding.etExerciseName.text?.toString()) {
                binding.etExerciseName.setText(uiState.exerciseName)
            }
            if (uiState.exerciseComment != binding.etExerciseComment.text?.toString()) {
                binding.etExerciseComment.setText(uiState.exerciseComment)
            }
            binding.rvSuperSet.isVisible = uiState.superSetList.isNotEmpty()
            adapter.items = uiState.superSetList
            binding.btnAction.text = uiState.actionButtonDescription
            if (binding.etExerciseName.adapter == null && uiState.autoCompleteExercise.isNotEmpty()) {
                binding.etExerciseName.setAdapter(
                    ArrayAdapter(requireContext(), R.layout.item_select_dialog, uiState.autoCompleteExercise)
                )
            }
        }
        viewModel.getExerciseCreateEventStream().observeWithLifecycle(this) { event ->
            when (event) {
                ExerciseCreateEvent.Default -> {}
                is ExerciseCreateEvent.ShowToast -> {
                    Toast.makeText(this.context, event.info, Toast.LENGTH_SHORT).show()
                    viewModel.onEventHandle()
                }

                ExerciseCreateEvent.Dismiss -> {
                    dismiss()
                    viewModel.onEventHandle()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.etExerciseName.setAdapter(null)
        binding.rvSuperSet.adapter = null
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {}
}
