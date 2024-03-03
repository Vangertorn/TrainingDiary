package com.yankin.training_exercises.impl.presentation.adapters.training_exercises

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SingleExerciseUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SuperSetUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId

class TrainingExercisesSwipeCallback(private val onSwipe: (id: TrainingExerciseId) -> Unit) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (viewHolder.itemViewType) {
            SingleExerciseUiModel.VIEW_TYPE -> {
                onSwipe(TrainingExerciseId.SingleExerciseId(viewHolder.itemId))
            }
            SuperSetUiModel.VIEW_TYPE -> onSwipe(TrainingExerciseId.SuperSetId(viewHolder.itemId))
        }
    }
}
