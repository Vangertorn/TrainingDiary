package com.yankin.common.recyclerview.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder


@Suppress("UNCHECKED_CAST")
inline fun <reified Payload> AdapterDelegateViewBindingViewHolder<*, *>.bindWithPayloads(
    crossinline bindPayload: (Payload) -> Unit,
    crossinline bindAll: () -> Unit
) {
    bind { rawPayloads: List<Any> ->
        if (rawPayloads.isEmpty()) {
            bindAll()
            return@bind
        }
        rawPayloads
            .flatMap { rawPayload -> rawPayload as Collection<Payload> }
            .forEach { payload: Payload -> bindPayload(payload) }
    }
}