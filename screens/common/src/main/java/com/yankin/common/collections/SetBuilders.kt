package com.yankin.common.collections

inline fun <E> buildSetOrNull(builderAction: MutableSet<E>.() -> Unit): MutableSet<E>? {
    val mutableList = mutableSetOf<E>()
    mutableList.builderAction()
    return mutableList.takeIf { result -> result.isNotEmpty() }
}