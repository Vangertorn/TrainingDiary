package com.yankin.settings.impl.presentation.settings.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.trainingdiary.settings.impl.databinding.ItemMuscleGroupBinding

internal fun muscleGroupAdapterDelegate(
    onMuscleGroupClickListener: (muscleGroupId: Long) -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<MuscleGroupUiModel, UiItem, ItemMuscleGroupBinding>(
        { layoutInflater, parent ->
            ItemMuscleGroupBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.root.debounceClick {
            onMuscleGroupClickListener.invoke(item.muscleGroupId)
        }
        bind {
            binding.tvMuscleGroup.text = item.description
        }
    }