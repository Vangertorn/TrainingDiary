package com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models

import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload
import com.yankin.training_exercises.impl.presentation.adapters.superset_exercises.SuperSetExerciseUiModel

internal data class SuperSetUiModel(
    override val id: TrainingExerciseId.SuperSetId,
    val superSetExercises: Payload.SuperSetExercises,
) : UiItem, TrainingExercise {
    sealed interface Payload : UiItemPayload {

        @JvmInline
        value class SuperSetExercises(val value: List<SuperSetExerciseUiModel>) : Payload
    }

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem.javaClass == newItem.javaClass
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is SuperSetUiModel && newItem is SuperSetUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.superSetExercises, newItem.superSetExercises)
            }
        } else {
            null
        }
    }

    companion object {
        const val VIEW_TYPE = 83
    }
}