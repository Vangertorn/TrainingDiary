package com.yankin.common.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams

fun View.setVerticalMargin(marginTop: Int = 0, marginBottom: Int = 0) {
    val _layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    _layoutParams.setMargins(
        _layoutParams.leftMargin,
        marginTop,
        _layoutParams.rightMargin,
        marginBottom
    )
    this.layoutParams = _layoutParams
}

fun View.addVerticalMargin(marginTop: Int = 0, marginBottom: Int = 0) {
    val _layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    _layoutParams.setMargins(
        0,
        marginTop + _layoutParams.topMargin,
        0,
        marginBottom + _layoutParams.bottomMargin
    )
    this.layoutParams = _layoutParams
}

fun View.removeFromParent() {
    if (parent != null) {
        (parent as ViewGroup).removeView(this)
    }
}

inline fun View.doOnSystemInsetsChanged(
    consumed: Boolean = true,
    crossinline block: (insets: WindowInsetsCompat) -> Unit
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        block(insets)
        if (consumed) {
            WindowInsetsCompat.CONSUMED
        } else {
            insets
        }
    }
}

fun View.updateMargin(
    @Px left: Int = marginLeft,
    @Px top: Int = marginTop,
    @Px right: Int = marginRight,
    @Px bottom: Int = marginBottom
) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        leftMargin = left
        topMargin = top
        rightMargin = right
        bottomMargin = bottom
    }
}
