package com.yankin.settings.impl.presentation.exercise_pattern_list.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.trainingdiary.settings.impl.databinding.ItemExercisePatternBinding

internal fun exercisePatternAdapterDelegate(
    onExercisePatternClickListener: (exercisePatternId: Long) -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<ExercisePatternUiModel, UiItem, ItemExercisePatternBinding>(
        { layoutInflater, parent ->
            ItemExercisePatternBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.root.debounceClick {
            onExercisePatternClickListener.invoke(item.exercisePatternId)
        }
        bindWithPayloads<ExercisePatternUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is ExercisePatternUiModel.Payload.Description ->
                        binding.tvNameExercise.text = payload.value
                }
            },
            bindAll = {
                binding.tvNameExercise.text = item.description.value
            }
        )
    }