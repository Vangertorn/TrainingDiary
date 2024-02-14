package com.yankin.exercise_create.impl.presentation.exercise_create.adapter.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class AddExerciseUiModel(
    val description: Payload.Description,
    val background: Payload.Background,
    val textColor: Payload.TextColor,
) : UiItem {
    sealed interface Payload : UiItemPayload {
        @JvmInline
        value class TextColor(@ColorRes val value: Int) : Payload

        @JvmInline
        value class Background(@DrawableRes val value: Int) : Payload

        @JvmInline
        value class Description(val value: String) : Payload
    }

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return if (oldItem is AddExerciseUiModel && newItem is AddExerciseUiModel) {
            oldItem.javaClass == newItem.javaClass
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is AddExerciseUiModel && newItem is AddExerciseUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.description, newItem.description)
                compareAndAdd(oldItem.background, newItem.background)
                compareAndAdd(oldItem.textColor, newItem.textColor)
            }
        } else {
            null
        }
    }
}