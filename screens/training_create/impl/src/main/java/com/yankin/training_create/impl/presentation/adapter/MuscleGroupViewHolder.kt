package com.yankin.training_create.impl.presentation.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.trainingdiary.training_create.impl.databinding.ItemMuscleGroupsBinding

internal fun muscleGroupAdapterDelegate(
    muscleGroupClickListener: (muscleGroupId: Long) -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<MuscleGroupUiModel, UiItem, ItemMuscleGroupsBinding>(
        { layoutInflater, parent ->
            ItemMuscleGroupsBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.root.debounceClick {
            muscleGroupClickListener.invoke(item.muscleGroupId)
        }
        bindWithPayloads<MuscleGroupUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is MuscleGroupUiModel.Payload.Background ->
                        binding.tvMuscleGroups.setBackgroundResource(payload.value)

                    is MuscleGroupUiModel.Payload.TextColor ->
                        binding.tvMuscleGroups.setTextColor(getColor(payload.value))
                }
            },
            bindAll = {
                binding.tvMuscleGroups.setBackgroundResource(item.background.value)
                binding.tvMuscleGroups.setTextColor(getColor(item.textColor.value))
                binding.tvMuscleGroups.text = item.description
            }
        )
    }