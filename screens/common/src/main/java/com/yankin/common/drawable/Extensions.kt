package com.yankin.common.drawable

import android.content.Context
import android.graphics.BlendModeColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import com.yankin.common.color_utils.ColorFilterMode
import com.yankin.common.color_utils.ColorUtils

fun Drawable?.setTintAttr(context: Context, @AttrRes attrId: Int) {
    this?.setColorFilter(
        ColorUtils.getColorAttr(context, attrId),
        ColorFilterMode.SRC_IN.getPorterDuffMode()
    )
}

fun Drawable?.setTintColor(context: Context, @ColorRes colorId: Int) {
    this?.setColorFilter(
        ColorUtils.getColor(context, colorId),
        ColorFilterMode.SRC_IN
    )
}

fun Drawable?.setColorFilter(color: Int, mode: ColorFilterMode) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this?.colorFilter = BlendModeColorFilter(color, mode.getBlendMode())
    } else {
        this?.setColorFilter(color, mode.getPorterDuffMode())
    }
}