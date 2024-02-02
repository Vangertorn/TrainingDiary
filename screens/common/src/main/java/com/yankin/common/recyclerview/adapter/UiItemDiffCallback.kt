package com.yankin.common.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil

object UiItemDiffCallback : DiffUtil.ItemCallback<UiItem>() {

    /**
     * @see UiItem.areItemsTheSame
     */
    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem.areItemsTheSame(oldItem, newItem)
    }

    /**
     * @see UiItem.areContentsTheSame
     */
    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
        return oldItem.areContentsTheSame(oldItem, newItem)
    }

    /**
     * @see UiItem.getChangePayload
     */
    override fun getChangePayload(oldItem: UiItem, newItem: UiItem): Any? {
        return oldItem.getChangePayload(oldItem, newItem)
    }
}