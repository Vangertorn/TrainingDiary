package com.yankin.exercise_create.impl.presentation.exercise_create.adapter.viewholders

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.exercise_create.impl.presentation.exercise_create.adapter.models.AddExerciseUiModel
import com.yankin.trainingdiary.exercise_create.impl.databinding.ItemSetBinding

internal fun addExerciseAdapterDelegate(
    addExerciseClickListener: () -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<AddExerciseUiModel, UiItem, ItemSetBinding>(
        { layoutInflater, parent ->
            ItemSetBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.root.debounceClick {
            addExerciseClickListener.invoke()
        }
        bindWithPayloads<AddExerciseUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is AddExerciseUiModel.Payload.Background -> binding.tvName.setBackgroundResource(payload.value)
                    is AddExerciseUiModel.Payload.Description -> binding.tvName.text = payload.value
                    is AddExerciseUiModel.Payload.TextColor -> binding.tvName.setTextColor(getColor(payload.value))
                }
            },
            bindAll = {
                binding.tvName.setBackgroundResource(item.background.value)
                binding.tvName.setTextColor(getColor(item.textColor.value))
                binding.tvName.text = item.description.value
            }
        )
    }
