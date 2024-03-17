package com.yankin.workout_routines.impl.presentation.adapters.training_exercises

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingBlockId
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingExercise

internal class WorkoutRoutinesSimpleCallback(
    private val recyclerView: RecyclerView,
    private val onSwipe: (id: TrainingBlockId) -> Unit,
    private val onSwitch: (firstTrainingBlockId: TrainingBlockId, secondTrainingBlockId: TrainingBlockId) -> Unit
) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean = true

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val firstTrainingBlockId =
            ((recyclerView.adapter as? WorkoutRoutinesAdapter)
                ?.items?.get(viewHolder.bindingAdapterPosition) as? TrainingExercise
                )?.id
        val secondTrainingBlockPosition = ((recyclerView.adapter as? WorkoutRoutinesAdapter)
            ?.items?.get(target.bindingAdapterPosition) as? TrainingExercise
            )?.id
        if (firstTrainingBlockId == null || secondTrainingBlockPosition == null) return false
        onSwitch(firstTrainingBlockId, secondTrainingBlockPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val itemPosition = viewHolder.absoluteAdapterPosition
        ((recyclerView.adapter as? WorkoutRoutinesAdapter)?.items?.get(itemPosition) as? TrainingExercise)?.run {
            onSwipe.invoke(id)
        }
    }
}
