package com.yankin.training_list.impl.presentation.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

internal class TrainingsSwipeCallback(
    private val recyclerView: RecyclerView,
    private val onSwipe: (trainingId: Long) -> Unit
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val itemPosition = viewHolder.absoluteAdapterPosition
        ((recyclerView.adapter as? TrainingsAdapter)?.items?.get(itemPosition) as? TrainingUiModel)?.run {
            onSwipe.invoke(trainingId)
        }
    }
}
