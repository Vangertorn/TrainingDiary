package com.yankin.common.debounce

internal open class PreviousTimestamp {
    protected var localPreviousClickTimestamp = 0L

    companion object {
        internal val defaultGlobalDebounceInterval = Interval.INTERVAL_200
        internal var globalPreviousClickTimestamp = 0L
    }
}