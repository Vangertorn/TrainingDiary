package com.yankin.trainingdiary.support.extensions

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.TextAppearanceSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.TextViewCompat
import com.example.trainingdiary.R

fun TextView.setTextAppearanceCompat(@StyleRes id: Int) {
    TextViewCompat.setTextAppearance(this, id)
}

fun TextView.hintTextColor(@ColorRes id: Int) {
    this.setHintTextColor(getColor(this.context, id))
}

fun TextView.textColor(@ColorRes id: Int) {
    this.setTextColor(getColor(this.context, id))
}

fun TextView.setVerticalMarginConstraintLayout(marginStart: Int, marginEnd: Int = marginStart) {
    (layoutParams as? ConstraintLayout.LayoutParams)?.apply {
        setMarginStart(marginStart)
        setMarginEnd(marginEnd)
    }
}

fun TextView.createClickableLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(text)
    var startIndexOfLink = -1

    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = getColor(context, R.color.crimson)
            }

            override fun onClick(view: View) {
                link.second.onClick(view)
            }
        }
        startIndexOfLink = text.toString().indexOf(link.first, startIndexOfLink + 1)
        if (startIndexOfLink == -1) continue

        spannableString.setSpannableParams(
            startIndexOfLink,
            startIndexOfLink + link.first.length,
            listOf(
                clickableSpan,
                TextAppearanceSpan(context, R.style.TextAppearance_AppCompat)
            )
        )
    }
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = getColor(context, R.color.black)
    setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun SpannableString.setSpannableParams(start: Int, end: Int, spannableParams: List<Any>) {
    spannableParams.forEach {
        setSpan(it, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

@SuppressLint("SetTextI18n")
fun TextView.setCategoryText(pathologyName: String, categoryName: String) {
    text = "$pathologyName | $categoryName"
}