package com.yankin.workout_routines.impl.presentation.adapters.superset_exercises

import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload
import com.yankin.workout_routines.impl.presentation.adapters.exercise_sets.ExerciseSetUiModel

internal data class SuperSetExerciseUiModel(
    val exerciseName: Payload.ExerciseName,
    val exerciseComment: Payload.ExerciseComment,
    val exerciseId: Long,
    val exerciseSets: Payload.ExerciseSets,
) : UiItem {
    sealed interface Payload : UiItemPayload {

        @JvmInline
        value class ExerciseName(val value: String) : Payload

        @JvmInline
        value class ExerciseComment(val value: String) : Payload

        @JvmInline
        value class ExerciseSets(val value: List<ExerciseSetUiModel>) : Payload
    }

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return if (oldItem is SuperSetExerciseUiModel && newItem is SuperSetExerciseUiModel) {
            oldItem.exerciseId == newItem.exerciseId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is SuperSetExerciseUiModel && newItem is SuperSetExerciseUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.exerciseName, newItem.exerciseName)
                compareAndAdd(oldItem.exerciseComment, newItem.exerciseComment)
                compareAndAdd(oldItem.exerciseSets, newItem.exerciseSets)
            }
        } else {
            null
        }
    }
}