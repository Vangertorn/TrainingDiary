package com.yankin.common.debounce

import android.os.SystemClock
import android.view.View
import com.yankin.common.debounce.PreviousTimestamp.Companion.defaultGlobalDebounceInterval
import com.yankin.common.debounce.PreviousTimestamp.Companion.globalPreviousClickTimestamp

internal class DebouncedOnClickListener(
    internal val minimumInterval: Interval = defaultGlobalDebounceInterval,
    private val globalClickTimestamp: Boolean = false,
    private val block: (View) -> Unit,
) : PreviousTimestamp(), View.OnClickListener {

    override fun onClick(clickedView: View) {
        val currentTimestamp = SystemClock.uptimeMillis()
        val clickTimestamp = if (globalClickTimestamp) {
            globalPreviousClickTimestamp
        } else {
            localPreviousClickTimestamp
        }
        if (currentTimestamp - clickTimestamp > minimumInterval.delay) {
            globalPreviousClickTimestamp = currentTimestamp
            localPreviousClickTimestamp = currentTimestamp
            block(clickedView)
        }
    }
}

fun doWithGlobalDebounce(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    block: () -> Boolean,
): Boolean {
    val currentTimestamp = SystemClock.uptimeMillis()
    return if (currentTimestamp - globalPreviousClickTimestamp > minimumInterval.delay) block() else false
}

fun updateGlobalDebounce() {
    globalPreviousClickTimestamp = SystemClock.uptimeMillis()
}

fun View.debounceClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Unit,
): View.OnClickListener {
    return DebouncedOnClickListener(
        minimumInterval = minimumInterval,
        block = function,
    ).apply {
        setOnClickListener(this)
    }
}

fun getDebounceClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Unit,
): View.OnClickListener {
    return DebouncedOnClickListener(minimumInterval) { view ->
        function(view)
    }
}

fun View.globalDebounceClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Unit,
): View.OnClickListener {
    return DebouncedOnClickListener(
        minimumInterval = minimumInterval,
        globalClickTimestamp = true,
        block = function,
    ).apply {
        setOnClickListener(this)
    }
}

fun getGlobalDebounceClick(
    minimumInterval: Interval = defaultGlobalDebounceInterval,
    function: (View) -> Unit,
): View.OnClickListener {
    return DebouncedOnClickListener(
        minimumInterval = minimumInterval,
        globalClickTimestamp = true,
    ) { view ->
        function(view)
    }
}