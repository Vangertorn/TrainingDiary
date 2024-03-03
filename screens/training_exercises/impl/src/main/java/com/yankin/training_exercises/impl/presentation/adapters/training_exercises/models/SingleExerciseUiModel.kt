package com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models

import com.yankin.common.collections.buildSetOrNull
import com.yankin.common.collections.compareAndAdd
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.UiItemPayload
import com.yankin.training_exercises.impl.presentation.adapters.exercise_sets.ExerciseSetUiModel

internal data class SingleExerciseUiModel(
    val exerciseName: Payload.ExerciseName,
    val exerciseComment: Payload.ExerciseComment,
    override val id: TrainingExerciseId.SingleExerciseId,
    val exerciseSets: Payload.ExerciseSets,
) : UiItem, TrainingExercise {
    sealed interface Payload : UiItemPayload {

        @JvmInline
        value class ExerciseName(val value: String) : Payload

        @JvmInline
        value class ExerciseComment(val value: String) : Payload

        @JvmInline
        value class ExerciseSets(val value: List<ExerciseSetUiModel>) : Payload
    }

    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return  oldItem.javaClass == newItem.javaClass
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>? {
        return if (oldItem is SingleExerciseUiModel && newItem is SingleExerciseUiModel) {
            buildSetOrNull {
                compareAndAdd(oldItem.exerciseName, newItem.exerciseName)
                compareAndAdd(oldItem.exerciseComment, newItem.exerciseComment)
                compareAndAdd(oldItem.exerciseSets, newItem.exerciseSets)
            }
        } else {
            null
        }
    }

    companion object{
        const val VIEW_TYPE = 84
    }
}