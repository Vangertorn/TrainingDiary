package com.yankin.common.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class SupportFragmentInset(@LayoutRes layoutResId: Int) :
    Fragment(layoutResId) {

    abstract fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean)

    protected var savedInsets: VerticalInset = VerticalInset.empty()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? OnSystemBarsSizeChangedListener)?.apply {
            activityChangedInsets(this.insets)
        }
    }

    private fun activityChangedInsets(lastVerticalInsets: VerticalInset) {
        savedInsets = lastVerticalInsets
        onInsetsReceived(lastVerticalInsets.top, lastVerticalInsets.bottom, savedInsets.hasKeyboard)
    }
}
