package com.yankin.core.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

typealias Mapper<T, R> = (state: @JvmSuppressWildcards T) -> R

fun <T, R> StateFlow<T>.mapStateFlow(
    coroutineScope: CoroutineScope,
    mapper: Mapper<T, R>
): StateFlow<R> = map { mapper(it) }.stateIn(
    coroutineScope,
    SharingStarted.Eagerly,
    mapper(value)
)
