package com.yankin.common.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.themes.currentTheme
import com.yankin.common.view.doOnSystemInsetsChanged
import com.yankin.common.view.updateMargin
import com.yankin.common.window.setSystemBarsAttrColor

abstract class BaseFragment<V : ViewBinding>(@LayoutRes layoutResId: Int) : SupportFragmentInset(layoutResId) {

    open val defaultColorRes: Int = android.R.attr.statusBarColor
    open val colorRes: Int = CommonRAttr.statusBarColor
    protected abstract val binding: V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onInject()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("onCreate", "Current screen: ${this::class.java.name}")
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTheme(requireContext().currentTheme)
        onApplyWindowInsets()
        onInitView(savedInstanceState)
        onObserveData()
    }

    protected open fun onApplyWindowInsets() {
        requireView().doOnSystemInsetsChanged { insets ->
            requireView().updateMargin(
                top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            )
        }
    }

    override fun onResume() {
        super.onResume()
        setSystemBarsColor()
    }

    protected open fun onInject() {}

    protected open fun onInitView(savedInstanceState: Bundle?) {}

    protected open fun onObserveData() {}

    protected open fun setSystemBarsColor() {
        val window = activity?.window ?: return
        window.setSystemBarsAttrColor(colorRes = colorRes, defaultColorRes = defaultColorRes)
    }
}