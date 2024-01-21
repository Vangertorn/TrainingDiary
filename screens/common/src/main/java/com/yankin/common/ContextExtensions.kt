package com.yankin.common

import android.app.Activity
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yankin.screens.common.R
import kotlin.math.roundToInt

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.createSpannableTermsAndPrivacy(
    onAgreementClickAction: (String) -> Unit
): SpannableString {
    val infoText = getString(R.string.Last_training_above)
    val termsText = getString(R.string.Last_training_above)
    val andText = getString(R.string.Last_training_above)
    val privacyText = getString(R.string.Last_training_above)
    val endingText = getString(R.string.Last_training_above)
    val result = SpannableString(
        String.format(
            "%s %s %s %s %s",
            infoText,
            termsText,
            andText,
            privacyText,
            endingText
        )
    )

    setNormalSpanText(
        result,
        0,
        infoText.length
    )

    setClickableSpanText(
        result,
        infoText.length,
        infoText.length + termsText.length + SINGLE_OFFSET
    ) { onAgreementClickAction.invoke("") }

    setNormalSpanText(
        result,
        termsText.length + infoText.length + DOUBLE_OFFSET,
        termsText.length + infoText.length + andText.length + TRIPLE_OFFSET
    )

    setClickableSpanText(
        result,
        andText.length + termsText.length + infoText.length + TRIPLE_OFFSET,
        andText.length + termsText.length + infoText.length + privacyText.length + TRIPLE_OFFSET
    ) { onAgreementClickAction.invoke("") }

    setNormalSpanText(
        result,
        termsText.length + infoText.length + andText.length + privacyText.length + TRIPLE_OFFSET,
        result.length
    )

    return result
}

fun Context.dpToPx(dp: Float) =
    (dp * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()

private fun Context.setNormalSpanText(text: SpannableString, start: Int, end: Int) {
    text.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this, R.color.black)),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

private fun Context.setClickableSpanText(
    text: SpannableString,
    start: Int,
    end: Int,
    onAgreementClickAction: () -> Unit
) {
    text.setSpan(
        object : ClickableSpan() {
            override fun onClick(widget: View) {
                onAgreementClickAction.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ContextCompat.getColor(this@setClickableSpanText, R.color.crimson)
            }
        },
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    text.setSpan(
        TextAppearanceSpan(this, R.style.TextAppearance_AppCompat),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

private const val SINGLE_OFFSET = 1
private const val DOUBLE_OFFSET = 2
private const val TRIPLE_OFFSET = 3
