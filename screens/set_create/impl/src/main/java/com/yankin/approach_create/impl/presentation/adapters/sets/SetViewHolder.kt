package com.yankin.approach_create.impl.presentation.adapters.sets

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.trainingdiary.approach_create.impl.databinding.ItemSetBinding

internal fun setAdapterDelegate(
    exerciseClickListener: (exerciseId: Long) -> Unit,
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<SetUiModel, UiItem, ItemSetBinding>(
        { layoutInflater, parent ->
            ItemSetBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.root.debounceClick {
            exerciseClickListener.invoke(item.setId)
        }
        bind {
            binding.tvApproach.text = item.description
        }
    }