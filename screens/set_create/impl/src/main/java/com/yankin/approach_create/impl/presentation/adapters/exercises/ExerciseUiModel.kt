package com.yankin.approach_create.impl.presentation.adapters.exercises

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class ExerciseUiModel(
    val exerciseName: Payload.ExerciseName,
    val exerciseId: Long,
    val background: Payload.Background,
    val textColor: Payload.TextColor,
) : UiItem {
    sealed interface Payload : UiItemPayload {
        @JvmInline
        value class TextColor(@ColorRes val value: Int) : Payload

        @JvmInline
        value class Background(@DrawableRes val value: Int) : Payload

        @JvmInline
        value class ExerciseName(val value: String) : Payload
    }

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return if (oldItem is ExerciseUiModel && newItem is ExerciseUiModel) {
            oldItem.exerciseId == newItem.exerciseId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is ExerciseUiModel && newItem is ExerciseUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.exerciseName, newItem.exerciseName)
                compareAndAdd(oldItem.background, newItem.background)
                compareAndAdd(oldItem.textColor, newItem.textColor)
            }
        } else {
            null
        }
    }
}