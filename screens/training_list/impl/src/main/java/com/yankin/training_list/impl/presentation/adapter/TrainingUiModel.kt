package com.yankin.training_list.impl.presentation.adapter

import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload

internal data class TrainingUiModel(
    val trainingDate: Payload.TrainingDate,
    val trainingComment: Payload.TrainingComment,
    val trainingId: Long,
    val muscleGroups: Payload.MuscleGroups,
    val personWeight: Payload.PersonWeight,
) : UiItem {
    sealed interface Payload : UiItemPayload {

        @JvmInline
        value class TrainingDate(val value: String) : Payload

        @JvmInline
        value class TrainingComment(val value: String) : Payload

        @JvmInline
        value class MuscleGroups(val value: String) : Payload

        @JvmInline
        value class PersonWeight(val value: String) : Payload
    }

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return if (oldItem is TrainingUiModel && newItem is TrainingUiModel) {
            oldItem.trainingId == newItem.trainingId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is TrainingUiModel && newItem is TrainingUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.trainingDate, newItem.trainingDate)
                compareAndAdd(oldItem.trainingComment, newItem.trainingComment)
                compareAndAdd(oldItem.muscleGroups, newItem.muscleGroups)
                compareAndAdd(oldItem.personWeight, newItem.personWeight)
            }
        } else {
            null
        }
    }
}