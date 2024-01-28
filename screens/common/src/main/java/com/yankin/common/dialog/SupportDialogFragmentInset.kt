package com.yankin.common.dialog

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yankin.common.fragment.OnSystemBarsSizeChangedListener
import com.yankin.common.fragment.VerticalInset

abstract class SupportDialogFragmentInset<T : ViewBinding>() :
    BottomSheetDialogFragment() {

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
        onInsetsReceived(
            top = lastVerticalInset.top,
            bottom = lastVerticalInset.bottom,
            hasKeyboard = lastVerticalInset.hasKeyboard
        )
    }
}
