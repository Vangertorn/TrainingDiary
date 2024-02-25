package com.yankin.exercise_create.impl.presentation.adapter.viewholders

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.exercise_create.impl.presentation.adapter.models.AddExerciseUiModel
import com.yankin.trainingdiary.exercise_create.impl.databinding.ItemExerciseExerciseCreateBinding

internal fun addExerciseAdapterDelegate(
    addExerciseClickListener: () -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<AddExerciseUiModel, UiItem, ItemExerciseExerciseCreateBinding>(
        { layoutInflater, parent ->
            ItemExerciseExerciseCreateBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.tvExerciseName.debounceClick {
            addExerciseClickListener.invoke()
        }
        bindWithPayloads<AddExerciseUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is AddExerciseUiModel.Payload.Background ->
                        binding.tvExerciseName.setBackgroundResource(payload.value)
                    is AddExerciseUiModel.Payload.Description ->
                        binding.tvExerciseName.text = payload.value
                    is AddExerciseUiModel.Payload.TextColor ->
                        binding.tvExerciseName.setTextColor(getColor(payload.value))
                }
            },
            bindAll = {
                binding.tvExerciseName.setBackgroundResource(item.background.value)
                binding.tvExerciseName.setTextColor(getColor(item.textColor.value))
                binding.tvExerciseName.text = item.description.value
            }
        )
    }