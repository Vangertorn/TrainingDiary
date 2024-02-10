package com.yankin.resource_manager.impl

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.yankin.resource_manager.api.StringProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class StringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StringProvider {

    override fun getLocalFormatString(
        str: String,
        vararg args: Any?,
        locale: Locale,
    ): String = String.format(locale, str, *args)

    override fun getString(
        @StringRes resource: Int,
        vararg formatArgs: Any?
    ): String = if (formatArgs.isEmpty()) {
        context.getString(resource)
    } else {
        context.getString(resource, *formatArgs)
    }

    override fun getPluralString(
        @PluralsRes pluralsRes: Int,
        count: Int
    ): String = context.resources.getQuantityString(pluralsRes, count, count)

    override fun getStrings(
        stringResList: List<Int>
    ): List<String> = stringResList.map { stringRes -> getString(stringRes) }

    override fun getStringByCondition(
        isCondition: Boolean,
        @StringRes first: Int,
        @StringRes second: Int
    ): String = context.resources.getString(if (isCondition) first else second)

    override fun getStringByConditionOrDefault(
        isCondition: Boolean,
        @StringRes stringRes: Int,
    ): String {
        return if (isCondition) context.resources.getString(stringRes) else ""
    }
}