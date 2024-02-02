package com.yankin.common.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.yankin.screens.common.BuildConfig

/**
 * Базовый адаптер для AsyncListDifferDelegationAdapter с установленным [fallbackAdapterDelegate] по умолчанию.
 */
abstract class BaseAsyncListDifferDelegationAdapter(
    callback: DiffUtil.ItemCallback<UiItem> = UiItemDiffCallback
) : AsyncListDifferDelegationAdapter<UiItem>(callback) {

    init {
        if (BuildConfig.DEBUG.not()) delegatesManager.fallbackDelegate = fallbackAdapterDelegate()
    }
}