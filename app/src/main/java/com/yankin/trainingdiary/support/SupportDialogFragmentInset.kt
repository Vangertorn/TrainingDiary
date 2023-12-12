package com.yankin.trainingdiary.support

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class SupportDialogFragmentInset<T : ViewBinding>(@LayoutRes layoutResId: Int) :
    DialogFragment(layoutResId), ViewBindable<T> {

    abstract fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean)

    protected var saveInsets: VerticalInset = VerticalInset.empty()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? OnSystemBarsSizeChangedListener)?.apply {
            activityChangedInsets(this.insets)
        }
    }

    private fun activityChangedInsets(lastVerticalInset: VerticalInset) {
        saveInsets = lastVerticalInset
        onInsetsReceived(lastVerticalInset.top, lastVerticalInset.bottom, saveInsets.hasKeyboard)
    }
}
