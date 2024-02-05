package com.yankin.kotlin

@Suppress("NOTHING_TO_INLINE")
inline fun <T> unsafeLazy(
    noinline initializer: () -> T
) = lazy(LazyThreadSafetyMode.NONE, initializer)