package com.yankin.training_exercises.impl.presentation.adapters.exercise_sets

import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class ExerciseSetUiModel(
    val description: String,
) : UiItem {

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
       return oldItem.javaClass == newItem.javaClass
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? = null
}