package com.yankin.room

import androidx.room.TypeConverter
import java.lang.StringBuilder

class Converters {

    @TypeConverter
    fun fromSelectedPositions(value: String): MutableList<Int> {
        return if (value.isEmpty()) {
            mutableListOf()
        } else {
            val list = mutableListOf<Int>()
            value.split(",").forEach { list.add(it.toInt()) }
            list
        }
    }

    @TypeConverter
    fun selectedPositionsToString(list: MutableList<Int>): String {
        return if (list.isEmpty()) {
            ""
        } else {
            val stringBuilder = StringBuilder()
            list.forEach {
                stringBuilder.append(it)
                stringBuilder.append(",")
            }
            stringBuilder.toString().removeSuffix(",")
        }
    }
}
