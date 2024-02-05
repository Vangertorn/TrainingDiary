package com.yankin.settings.impl.presentation.exercise_pattern_create

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.debounce.globalDebounceClick
import com.yankin.common.dialog.BaseBottomSheetDialogFragment
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedParamsViewModelFactory
import com.yankin.navigation.BundleParcelable
import com.yankin.settings.api.navigation.SettingsCommunicator
import com.yankin.settings.impl.di.ExercisePatternCreateViewModelFactory
import com.yankin.settings.impl.navigation.ExercisePatternCreateDialogParams
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.BottomSheetExercisePatternCreateBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class ExercisePatternCreateBottomDialog
    : BaseBottomSheetDialogFragment<BottomSheetExercisePatternCreateBinding>() {

    @Inject
    lateinit var viewModelFactory: ExercisePatternCreateViewModelFactory

    override fun parentLayoutId(): Int = R.id.exercisePatternCreateDialog
    override fun attrColorBackground(): Int = CommonRAttr.contentBackground
    override val binding: BottomSheetExercisePatternCreateBinding
        by viewBinding(BottomSheetExercisePatternCreateBinding::inflate)
    private val viewModel: ExercisePatternCreateViewModel by viewModels {
        AssistedParamsViewModelFactory(params = params, factory = viewModelFactory, owner = this)
    }
    private var params by BundleParcelable<ExercisePatternCreateDialogParams>(SettingsCommunicator.NAV_KEY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etCommentExercise.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onExerciseNameChange(text ?: "")
            }
        }
        binding.btnClose.globalDebounceClick {
            dismiss()
        }
        binding.btnDelete.globalDebounceClick {
            viewModel.onDeleteExercisePatternClick()
            dismiss()
        }
        binding.btnSave.globalDebounceClick {
            viewModel.onSaveExercisePatternClick()
            dismiss()
        }
    }

    override fun onObserveData() {
        viewModel.getExercisePatternCreateUiStream().observeWithLifecycle(this) { uiState ->
            if (uiState.exercisePatternName != binding.etCommentExercise.text?.toString()) {
                binding.etCommentExercise.setText(uiState.exercisePatternName)
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {}
}
