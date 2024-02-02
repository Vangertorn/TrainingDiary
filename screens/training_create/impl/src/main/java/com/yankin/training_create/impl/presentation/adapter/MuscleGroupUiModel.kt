package com.yankin.training_create.impl.presentation.adapter

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class MuscleGroupUiModel(
    val description: String,
    val muscleGroupId: Long,
    val background: Payload.Background,
    val textColor: Payload.TextColor,
) : UiItem {
    sealed interface Payload : UiItemPayload {
        @JvmInline
        value class TextColor(@ColorRes val value: Int) : Payload

        @JvmInline
        value class Background(@DrawableRes val value: Int) : Payload
    }

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

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is MuscleGroupUiModel && newItem is MuscleGroupUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.background, newItem.background)
                compareAndAdd(oldItem.textColor, newItem.textColor)
            }
        } else {
            null
        }
    }
}