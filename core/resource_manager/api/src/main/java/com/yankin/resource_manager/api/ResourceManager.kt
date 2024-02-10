package com.yankin.resource_manager.api

import java.util.Locale

interface ResourceManager {

    fun getLocalFormatString(
        str: String,
        vararg args: Any?,
        locale: Locale = Locale.ENGLISH,
    ): String

    fun getString(resource: Int, vararg formatArgs: Any?): String

    fun getPluralString(pluralsRes: Int, count: Int): String

    fun getStrings(stringResList: List<Int>): List<String>

    fun getStringByCondition(
        isCondition: Boolean,
        first: Int,
        second: Int,
    ): String

    fun getStringByConditionOrDefault(
        isCondition: Boolean,
        stringRes: Int,
    ): String
}
