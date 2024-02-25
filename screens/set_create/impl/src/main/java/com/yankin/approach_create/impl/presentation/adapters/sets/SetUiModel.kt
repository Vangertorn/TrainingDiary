package com.yankin.approach_create.impl.presentation.adapters.sets

import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class SetUiModel(
    val setId: Long,
    val description: String,
) : UiItem {

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return if (oldItem is SetUiModel && newItem is SetUiModel) {
            oldItem.setId == newItem.setId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? = null
}