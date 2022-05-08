package com.yankin.trainingdiary.support

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeAndMoveCallback(
    private val onMove: (Int, Int) -> Unit,
    private val onSwipe: (Int, Int) -> Unit

) :
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT.or(
            ItemTouchHelper.RIGHT
        )
    ) {
    override fun onMove(
        recyclerView: RecyclerView,
        p1: RecyclerView.ViewHolder,
        p2: RecyclerView.ViewHolder
    ): Boolean {
        recyclerView.adapter?.notifyItemMoved(p1.adapterPosition, p2.adapterPosition)
        onMove(p1.adapterPosition, p2.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipe(viewHolder.adapterPosition, direction)
    }
}
