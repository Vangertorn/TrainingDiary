package com.yankin.common.recyclerview.adapter

interface UiItem {

    fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean

    fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean

    fun getChangePayload(oldItem: UiItem, newItem: UiItem): Collection<UiItemPayload>?
}