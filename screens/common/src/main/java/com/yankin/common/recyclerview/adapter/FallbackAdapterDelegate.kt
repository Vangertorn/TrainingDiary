package com.yankin.common.recyclerview.adapter

import android.util.Log
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.screens.common.databinding.ItemFallbackAdapterDelegateBinding

fun fallbackAdapterDelegate(): AdapterDelegate<List<UiItem>> = adapterDelegateViewBinding(
    { layoutInflater, parent -> ItemFallbackAdapterDelegateBinding.inflate(layoutInflater, parent, false) }
) {
    bind {
        Log.e("fallbackAdapterDelegate", "AdapterDelegate not found for ${item::class.simpleName}")
    }
}