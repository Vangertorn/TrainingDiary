package com.yankin.trainingdiary.support.extensions

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import com.yankin.common.resource_import.CommonRColor
import java.io.File

fun File.makeDirIfNotExist() = if (this.exists()) mkdir() else false

fun String?.orDash(): String {
    return if (this.isNullOrBlank()) "-"
    else this
}

fun String.isBlankOrDash(): Boolean {
    return if (isBlank() || this == "-") return true
    else false
}

fun String.getSelectionSearchText(
    searchText: String,
    context: Context
): SpannableString {
    val startPos = indexOf(string = searchText, ignoreCase = true)
    val endPos = startPos + searchText.length
    val spannable = SpannableString(this)
    if (searchText.length > 2) {
        spannable.setSpan(
            BackgroundColorSpan(context.getColor(CommonRColor.black)),
            startPos,
            endPos,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannable
}
