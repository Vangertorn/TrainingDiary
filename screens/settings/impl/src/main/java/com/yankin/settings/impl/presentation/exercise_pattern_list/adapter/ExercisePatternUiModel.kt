package com.yankin.settings.impl.presentation.exercise_pattern_list.adapter

import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class ExercisePatternUiModel(
    val description: Payload.Description,
    val exercisePatternId: Long,
) : UiItem {
    sealed interface Payload : UiItemPayload {
        @JvmInline
        value class Description(val value: String) : Payload
    }

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return if (oldItem is ExercisePatternUiModel && newItem is ExercisePatternUiModel) {
            oldItem.exercisePatternId == newItem.exercisePatternId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is ExercisePatternUiModel && newItem is ExercisePatternUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.description, newItem.description)
            }
        } else {
            null
        }
    }
}