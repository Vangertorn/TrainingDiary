package com.yankin.common.recyclerview.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder


@Suppress("UNCHECKED_CAST")
inline fun <reified T> AdapterDelegateViewBindingViewHolder<*, *>.bindWithPayloads(
    crossinline bindPayload: (T) -> Unit,
    crossinline bindAll: () -> Unit
) {
    bind { rawPayloads: List<Any> ->
        if (rawPayloads.isEmpty()) {
            bindAll()
            return@bind
        }
        rawPayloads
            .flatMap { rawPayload -> rawPayload as Collection<T> }
            .forEach { payload: T -> bindPayload(payload) }
    }
}