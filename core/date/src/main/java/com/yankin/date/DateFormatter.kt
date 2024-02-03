package com.yankin.date

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateFormatter {

    const val DD_POINT_MM_POINT_YY = "dd.MM.yy"


    val timeZone: Int
        get() = TimeZone.getDefault().getOffset(Date().time) / 1000 / 60 / 60

    fun dateToString(
        date: Date,
        dateFormat: String,
        locale: Locale = Locale.getDefault()
    ): String = SimpleDateFormat(dateFormat, locale).format(date)

    @Suppress("SimpleDateFormat")
    fun convertStringToDate(dateStr: String, pattern: String): Date {
        var date = Date()
        try {
            date = SimpleDateFormat(pattern).parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }
}