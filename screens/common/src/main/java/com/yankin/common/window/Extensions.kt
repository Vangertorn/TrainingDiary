package com.yankin.common.window

import android.content.Context
import android.os.Build
import android.view.Window
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.yankin.common.color_utils.ColorUtils

fun Window.setSystemBarsAttrColor(
    context: Context = this.context,
    @AttrRes colorRes: Int,
    @AttrRes defaultColorRes: Int,
) {
    val lightStatusBar = colorRes != defaultColorRes
    statusBarColor = ColorUtils.getColorAttr(context, colorRes)
    navigationBarColor = ColorUtils.getColorAttr(context, colorRes)
    updateSystemBarsContentColor(lightStatusBar, lightStatusBar)
}

fun Window.setSystemBarsColor(
    @ColorRes colorRes: Int,
    @ColorRes defaultColorRes: Int,
    nightMode: Boolean,
    context: Context = this.context
) {
    val lightStatusBar = colorRes != defaultColorRes && !nightMode
    statusBarColor = ContextCompat.getColor(context, colorRes)
    navigationBarColor = ContextCompat.getColor(context, colorRes)
    updateSystemBarsContentColor(lightStatusBar, lightStatusBar)
}

fun Window.setStatusBarColor(
    context: Context,
    @ColorRes colorRes: Int,
    @ColorRes defaultColorRes: Int,
    nightMode: Boolean
) {
    val lightStatusBar = colorRes != defaultColorRes && !nightMode
    statusBarColor = ColorUtils.getColor(context, colorRes)
    updateSystemBarsContentColor(lightStatusBar, lightStatusBar)
}

fun Window.setSystemBarsAttrColor(
    context: Context,
    @AttrRes statusBarColorAttr: Int,
    @AttrRes navigationBarColorAttr: Int,
    lightStatusBar: Boolean,
    lightNavigationBar: Boolean
) {
    val statusBarColorRes = ColorUtils.getColorAttr(context, statusBarColorAttr, true)
    val navigationBarColorRes = ColorUtils.getColorAttr(context, navigationBarColorAttr, true)
    setSystemBarsColor(context, statusBarColorRes, navigationBarColorRes, lightStatusBar, lightNavigationBar)
}

fun Window.setSystemBarsColor(
    context: Context,
    @ColorRes statusBarColorRes: Int,
    @ColorRes navigationBarColorRes: Int,
    lightStatusBar: Boolean,
    lightNavigationBar: Boolean
) {
    statusBarColor = ColorUtils.getColor(context, statusBarColorRes)
    navigationBarColor = ColorUtils.getColor(context, navigationBarColorRes)
    updateSystemBarsContentColor(lightStatusBar, lightNavigationBar)
}

private fun Window.updateSystemBarsContentColor(lightStatusBar: Boolean, lightNavigationBar: Boolean) {
    WindowInsetsControllerCompat(this, decorView).isAppearanceLightStatusBars = lightStatusBar
    WindowInsetsControllerCompat(this, decorView).isAppearanceLightNavigationBars = lightNavigationBar
}

fun Window.copySystemNavigationBarColorFromWindow(parentWindow: Window) {
    navigationBarColor = parentWindow.navigationBarColor
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            insetsController?.apply {
                parentWindow.insetsController?.systemBarsAppearance?.also { flags ->
                    setSystemBarsAppearance(flags, flags)
                }
            }
        }

        else -> {
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility = parentWindow.decorView.systemUiVisibility
        }
    }
}