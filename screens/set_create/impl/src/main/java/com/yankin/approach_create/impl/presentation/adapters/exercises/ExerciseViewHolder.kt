package com.yankin.approach_create.impl.presentation.adapters.exercises

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.trainingdiary.approach_create.impl.databinding.ItemExerciseBinding

internal fun exerciseAdapterDelegate(
    exerciseClickListener: (exerciseId: Long) -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<ExerciseUiModel, UiItem, ItemExerciseBinding>(
        { layoutInflater, parent ->
            ItemExerciseBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.root.debounceClick {
            exerciseClickListener.invoke(item.exerciseId)
        }
        bindWithPayloads<ExerciseUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is ExerciseUiModel.Payload.Background ->
                        binding.tvName.setBackgroundResource(payload.value)

                    is ExerciseUiModel.Payload.TextColor ->
                        binding.tvName.setTextColor(getColor(payload.value))

                    is ExerciseUiModel.Payload.ExerciseName -> {
                        binding.tvName.text = payload.value
                    }
                }
            },
            bindAll = {
                binding.tvName.setBackgroundResource(item.background.value)
                binding.tvName.setTextColor(getColor(item.textColor.value))
                binding.tvName.text = item.exerciseName.value
            }
        )
    }