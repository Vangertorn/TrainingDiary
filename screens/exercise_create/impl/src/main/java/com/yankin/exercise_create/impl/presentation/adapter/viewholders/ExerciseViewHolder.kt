package com.yankin.exercise_create.impl.presentation.adapter.viewholders

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.exercise_create.impl.presentation.adapter.models.ExerciseUiModel
import com.yankin.trainingdiary.exercise_create.impl.databinding.ItemExerciseExerciseCreateBinding

internal fun exerciseAdapterDelegate(
    exerciseClickListener: (exerciseId: Int) -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<ExerciseUiModel, UiItem, ItemExerciseExerciseCreateBinding>(
        { layoutInflater, parent ->
            ItemExerciseExerciseCreateBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.tvExerciseName.debounceClick {
            exerciseClickListener.invoke(item.exerciseId)
        }
        bindWithPayloads<ExerciseUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is ExerciseUiModel.Payload.Background ->
                        binding.tvExerciseName.setBackgroundResource(payload.value)
                    is ExerciseUiModel.Payload.TextColor ->
                        binding.tvExerciseName.setTextColor(getColor(payload.value))
                    is ExerciseUiModel.Payload.ExerciseName -> {
                        binding.tvExerciseName.text = payload.value
                    }
                }
            },
            bindAll = {
                binding.tvExerciseName.setBackgroundResource(item.background.value)
                binding.tvExerciseName.setTextColor(getColor(item.textColor.value))
                binding.tvExerciseName.text = item.exerciseName.value
            }
        )
    }