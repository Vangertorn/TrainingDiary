package com.yankin.common.collections

fun <T> MutableCollection<T>.compareAndAdd(old: T, new: T) {
    if (old != new) {
        add(new)
    }
}