package com.yankin.common.debounce

import android.os.SystemClock
import android.view.View
import com.yankin.common.debounce.PreviousTimestamp.Companion.defaultGlobalDebounceInterval

internal class DebouncedOnLongClickListener(
    private val minimumInterval: Interval = defaultGlobalDebounceInterval,
    private val globalClickTimestamp: Boolean = false,
    private val block: (View) -> Boolean,
) : PreviousTimestamp(), View.OnLongClickListener {

    override fun onLongClick(clickedView: View): Boolean {
        val currentTimestamp = SystemClock.uptimeMillis()
        val clickTimestamp = if (globalClickTimestamp) {
            globalPreviousClickTimestamp
        } else {
            localPreviousClickTimestamp
        }
        if (currentTimestamp - clickTimestamp > minimumInterval.delay) {
            globalPreviousClickTimestamp = currentTimestamp
            localPreviousClickTimestamp = currentTimestamp
            return block(clickedView)
        }
        return false
    }
}

fun View.debounceLongClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Boolean,
): View.OnLongClickListener {
    return DebouncedOnLongClickListener(
        minimumInterval = minimumInterval,
        block = function,
    ).apply {
        setOnLongClickListener(this)
    }
}

fun getDebounceLongClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Boolean,
): View.OnLongClickListener {
    return DebouncedOnLongClickListener(minimumInterval) { view ->
        function(view)
    }
}

fun View.globalDebounceLongClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Boolean,
): View.OnLongClickListener {
    return DebouncedOnLongClickListener(
        minimumInterval = minimumInterval,
        globalClickTimestamp = true,
        block = function,
    ).apply {
        setOnLongClickListener(this)
    }
}

fun getGlobalDebounceLongClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Boolean,
): View.OnLongClickListener {
    return DebouncedOnLongClickListener(
        minimumInterval = minimumInterval,
        globalClickTimestamp = true,
        block = function,
    )
}