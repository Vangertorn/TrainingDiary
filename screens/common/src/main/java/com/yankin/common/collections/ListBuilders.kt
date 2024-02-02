package com.yankin.common.collections

inline fun <E> buildListOrNull(builderAction: MutableList<E>.() -> Unit): List<E>? {
    val mutableList = mutableListOf<E>()
    mutableList.builderAction()
    return mutableList.takeIf { result -> result.isNotEmpty() }
}