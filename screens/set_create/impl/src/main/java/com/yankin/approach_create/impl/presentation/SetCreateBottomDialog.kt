package com.yankin.approach_create.impl.presentation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.yankin.approach_create.api.navigation.SetCreateCommunicator
import com.yankin.approach_create.impl.di.SetCreateViewModelFactory
import com.yankin.approach_create.impl.navigation.SetCreateParcelableParams
import com.yankin.approach_create.impl.presentation.adapters.exercises.ExerciseAdapter
import com.yankin.approach_create.impl.presentation.adapters.sets.SetAdapter
import com.yankin.approach_create.impl.presentation.models.SetCreateEvent
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.debounce.debounceClick
import com.yankin.common.dialog.BaseBottomSheetDialogFragment
import com.yankin.common.hideKeyboard
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedParamsViewModelFactory
import com.yankin.kotlin.unsafeLazy
import com.yankin.navigation.BundleParcelable
import com.yankin.trainingdiary.approach_create.impl.R
import com.yankin.trainingdiary.approach_create.impl.databinding.BottomSheetAddSetBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class SetCreateBottomDialog : BaseBottomSheetDialogFragment<BottomSheetAddSetBinding>() {

    @Inject
    lateinit var viewModelFactory: SetCreateViewModelFactory

    override fun parentLayoutId(): Int = R.id.addSetDialog
    override fun attrColorBackground(): Int = CommonRAttr.contentBackground
    override val binding: BottomSheetAddSetBinding by viewBinding(BottomSheetAddSetBinding::inflate)
    private val viewModel: SetCreateViewModel by viewModels {
        AssistedParamsViewModelFactory(params = params, factory = viewModelFactory, owner = this)
    }
    private val params by BundleParcelable<SetCreateParcelableParams>(SetCreateCommunicator.NAV_KEY)

    private val setAdapter by unsafeLazy { SetAdapter(setClickListener = viewModel::onSetClick) }
    private val exerciseAdapter by unsafeLazy { ExerciseAdapter(exerciseClickListener = viewModel::onExerciseClick) }
    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            binding.rvSets.scrollToPosition(setAdapter.itemCount - 1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.acTvExerciseName.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onExerciseNameChange(text ?: "")
            }
        }
        binding.etCommentExercise.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onCommentChange(text ?: "")
            }
        }
        binding.btnAddSet.debounceClick {
            viewModel.onAddSetClick()
            hideKeyboard()
        }
        binding.btnEditExercise.debounceClick {
            viewModel.onUpdateExerciseClick()
            hideKeyboard()
        }
        binding.etReps.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onRepsChange(text ?: "")
            }
        }
        binding.etWeight.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onWeightChange(text ?: "")
            }
        }
        binding.ibAddQuantity.setOnClickListener { viewModel.onRepsCountUpClick() }
        binding.ibRemoveQuantity.setOnClickListener { viewModel.onRepsCountDownClick() }
        binding.ibAddWeight.setOnClickListener { viewModel.onWeightUpClick() }
        binding.ibRemoveWeight.setOnClickListener { viewModel.onWeightDownClick() }
    }

    override fun onInitView() {
        binding.rvSets.adapter = setAdapter
        binding.rvExercises.adapter = exerciseAdapter
        setAdapter.registerAdapterDataObserver(dataObserver)
    }

    override fun onObserveData() {
        viewModel.getSetCreateUiStream().observeWithLifecycle(this) { uiState ->
            if (uiState.exerciseName != binding.acTvExerciseName.text?.toString()) {
                binding.acTvExerciseName.setText(uiState.exerciseName)
            }
            if (uiState.exerciseComment != binding.etCommentExercise.text?.toString()) {
                binding.etCommentExercise.setText(uiState.exerciseComment)
            }
            if (uiState.weight != binding.etWeight.text?.toString()) {
                binding.etWeight.setText(uiState.weight)
            }
            if (uiState.reps != binding.etReps.text?.toString()) {
                binding.etReps.setText(uiState.reps)
            }
            binding.rvExercises.isVisible = uiState.exerciseList.isNotEmpty()
            setAdapter.items = uiState.setList
            exerciseAdapter.items = uiState.exerciseList
            if (binding.acTvExerciseName.adapter == null && uiState.autoCompleteExercise.isNotEmpty()) {
                binding.acTvExerciseName.setAdapter(
                    ArrayAdapter(requireContext(), R.layout.item_select_dialog, uiState.autoCompleteExercise)
                )
            }
        }
        viewModel.getSetCreateEventStream().observeWithLifecycle(this) { event ->
            when (event) {
                SetCreateEvent.Default -> {}
                is SetCreateEvent.ShowToast -> {
                    Toast.makeText(this.context, event.info, Toast.LENGTH_SHORT).show()
                    viewModel.onEventHandle()
                }
            }
        }
    }

    override fun onDestroyView() {
        setAdapter.unregisterAdapterDataObserver(dataObserver)
        super.onDestroyView()
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {}
}
