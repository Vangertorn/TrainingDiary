package com.yankin.room

import androidx.room.TypeConverter

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

    @TypeConverter
    fun String.toListLong(): List<Long> {
        return if (isEmpty()) {
            emptyList()
        } else {
            val resultList = mutableListOf<Long>()
            split(",").forEach { resultList.add(it.toLong()) }
            resultList
        }
    }

    @TypeConverter
    fun List<Long>.toStringForDataBase(): String {
        return if (isEmpty()) {
            ""
        } else {
            val stringBuilder = StringBuilder()
            forEach { valueLong ->
                stringBuilder.append(valueLong)
                stringBuilder.append(",")
            }
            stringBuilder.toString().removeSuffix(",")
        }
    }
}
