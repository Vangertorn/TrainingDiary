package com.yankin.common.activity

import android.app.Activity
import android.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.makeTransparentStatusBar() {
    window.statusBarColor = Color.TRANSPARENT
}

fun Activity.makeTransparentNavigationBar() {
    window.navigationBarColor = Color.TRANSPARENT
}

fun Activity.immersiveMode(enabled: Boolean) {
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    if (enabled) {
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    } else {
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }
}