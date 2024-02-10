package com.yankin.resource_manager.impl

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.resource_manager.api.StringProvider
import java.util.Locale
import javax.inject.Inject

class ResourceManagerImpl @Inject constructor(
    private val stringProvider: StringProvider,
) : ResourceManager {

    override fun getLocalFormatString(
        str: String,
        vararg args: Any?,
        locale: Locale,
    ): String = String.format(locale, str, *args)

    override fun getString(
        @StringRes resource: Int,
        vararg formatArgs: Any?
    ): String = stringProvider.getString(resource, *formatArgs)

    override fun getPluralString(
        @PluralsRes pluralsRes: Int,
        count: Int
    ): String = stringProvider.getPluralString(pluralsRes, count)

    override fun getStrings(
        stringResList: List<Int>
    ): List<String> = stringProvider.getStrings(stringResList)

    override fun getStringByCondition(
        isCondition: Boolean,
        @StringRes first: Int,
        @StringRes second: Int
    ): String = stringProvider.getStringByCondition(isCondition, first, second)

    override fun getStringByConditionOrDefault(
        isCondition: Boolean,
        @StringRes stringRes: Int,
    ): String = stringProvider.getStringByConditionOrDefault(isCondition, stringRes)
}