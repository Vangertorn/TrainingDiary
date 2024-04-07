package com.yankin.common.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yankin.common.fragment.OnSystemBarsSizeChangedListener
import com.yankin.common.fragment.SupportFragmentInset
import com.yankin.common.fragment.VerticalInset

abstract class SupportActivityInset: AppCompatActivity(), OnSystemBarsSizeChangedListener {

    override var insets: VerticalInset = VerticalInset.empty()

    abstract fun getActiveFragment(): Fragment?

    override fun insetsChanged(statusBarSize: Int, navigationBarSize: Int, hasKeyboard: Boolean) {
        insets = VerticalInset(statusBarSize, navigationBarSize, hasKeyboard)
        val fragment = getActiveFragment()
        if (fragment != null && fragment is SupportFragmentInset) {
            fragment.onInsetsReceived(statusBarSize, navigationBarSize, hasKeyboard)
        }
    }
}
