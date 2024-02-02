package com.yankin.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

sealed interface Timestamp {

    val value: Long

    @JvmInline
    value class Seconds(override val value: Long) : Timestamp {
        operator fun minus(other: Seconds): Seconds = Seconds(value - other.value)
        operator fun plus(other: Seconds): Seconds = Seconds(value + other.value)
        operator fun compareTo(other: Seconds): Int = value.compareTo(other.value)
    }

    @JvmInline
    value class Milliseconds(override val value: Long) : Timestamp {
        operator fun minus(other: Milliseconds): Milliseconds = Milliseconds(value - other.value)
        operator fun plus(other: Milliseconds): Milliseconds = Milliseconds(value + other.value)
        operator fun compareTo(other: Milliseconds): Int = value.compareTo(other.value)
    }

    fun getMilliseconds(): Long = when (this) {
        is Seconds -> value * 1000L
        is Milliseconds -> value
    }

    fun getSeconds(): Long = when (this) {
        is Seconds -> value
        is Milliseconds -> value / 1000L
    }

    fun toDateString(
        dateFormat: String,
        locale: Locale = Locale.getDefault()
    ): String = SimpleDateFormat(dateFormat, locale).format(timestampToDate())

    private fun timestampToDate(): Date =
        GregorianCalendar().apply {
            clear()
            timeInMillis = getMilliseconds()
        }.time
}