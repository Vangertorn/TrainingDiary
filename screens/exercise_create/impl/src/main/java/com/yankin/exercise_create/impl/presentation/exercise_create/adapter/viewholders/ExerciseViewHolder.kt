package com.yankin.exercise_create.impl.presentation.exercise_create.adapter.viewholders

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.exercise_create.impl.presentation.exercise_create.adapter.models.ExerciseUiModel
import com.yankin.trainingdiary.exercise_create.impl.databinding.ItemSetBinding

internal fun exerciseAdapterDelegate(
    exerciseClickListener: (exerciseId: Int) -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<ExerciseUiModel, UiItem, ItemSetBinding>(
        { layoutInflater, parent ->
            ItemSetBinding.inflate(layoutInflater, parent, false)
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