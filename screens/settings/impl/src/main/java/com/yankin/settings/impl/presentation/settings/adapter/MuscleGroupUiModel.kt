package com.yankin.settings.impl.presentation.settings.adapter

import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class MuscleGroupUiModel(
    val description: String,
    val muscleGroupId: Long,
) : UiItem {

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return if (oldItem is MuscleGroupUiModel && newItem is MuscleGroupUiModel) {
            oldItem.muscleGroupId == newItem.muscleGroupId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? = null
}