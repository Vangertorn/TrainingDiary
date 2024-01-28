package com.yankin.common.color_utils

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.util.TypedValue
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.yankin.common.resource_import.CommonRColor

object ColorUtils {
    private var typedValue: TypedValue = TypedValue()

    fun getColor(context: Context, @ColorRes color: Int): Int =
        ContextCompat.getColor(context, color)

    fun getColorAttr(context: Context, @AttrRes attrId: Int, needResId: Boolean = false): Int {
        context.theme.resolveAttribute(attrId, typedValue, true)
        return if (needResId) typedValue.resourceId else typedValue.data
    }

    fun getColorStateList(
        context: Context,
        @ColorRes focusedColor: Int,
        @ColorRes unfocusedColor: Int
    ): ColorStateList = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf()),
        intArrayOf(getColor(context, focusedColor), getColor(context, unfocusedColor))
    )

    fun getColorStateListByAttr(
        context: Context,
        @AttrRes focusedColor: Int,
        @AttrRes unfocusedColor: Int
    ): ColorStateList = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf()),
        intArrayOf(getColorAttr(context, focusedColor), getColorAttr(context, unfocusedColor))
    )

    fun getColorStateListSelectedByAttr(
        context: Context,
        @AttrRes selectedColor: Int,
        @AttrRes unselectedColor: Int
    ): ColorStateList = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf()),
        intArrayOf(getColorAttr(context, selectedColor), getColorAttr(context, unselectedColor))
    )

    fun getRgbColor(alpha: Int, red: Int, green: Int, blue: Int): Int {
        return Color.argb(alpha, red, green, blue)
    }

    fun setProgressColor(context: Context, progress: ProgressBar) {
        progress.indeterminateDrawable
            .setColorFilterByRes(context,CommonRColor.white, ColorFilterMode.SRC_IN)
    }

    fun changeTextColors(textView: TextView, @ColorInt colorTo: Int, animate: Boolean = true) {
        if (animate) {
            val from = FloatArray(3)
            val to = FloatArray(3)

            Color.colorToHSV(textView.currentTextColor, from)
            Color.colorToHSV(colorTo, to)

            val anim = ValueAnimator.ofFloat(0f, 1f)
            anim.duration = 300

            val hsv = FloatArray(3)
            anim.addUpdateListener {
                hsv[0] = from[0] + (to[0] - from[0]) * it.animatedFraction
                hsv[1] = from[1] + (to[1] - from[1]) * it.animatedFraction
                hsv[2] = from[2] + (to[2] - from[2]) * it.animatedFraction

                textView.setTextColor(Color.HSVToColor(hsv))
            }
            anim.start()
        } else
            textView.setTextColor(colorTo)
    }

    fun tintDrawable(drawable: Drawable, @ColorInt color: Int): Drawable {
        (drawable as? VectorDrawableCompat)
            ?.apply { setTintList(ColorStateList.valueOf(color)) }
            ?.let { return it }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (drawable as? VectorDrawable)
                ?.apply { setTintList(ColorStateList.valueOf(color)) }
                ?.let { return it }
        }

        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(wrappedDrawable, color)
        return DrawableCompat.unwrap(wrappedDrawable)
    }
}

fun Drawable?.setColorFilter(color: Int, mode: ColorFilterMode) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this?.colorFilter = BlendModeColorFilter(color, mode.getBlendMode())
    } else {
        this?.setColorFilter(color, mode.getPorterDuffMode())
    }
}

fun Drawable?.setColorFilterByRes(
    context: Context,
    @ColorRes color: Int,
    mode: ColorFilterMode = ColorFilterMode.SRC_IN
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this?.colorFilter = BlendModeColorFilter(ColorUtils.getColor(context, color), mode.getBlendMode())
    } else {
        this?.setColorFilter(ColorUtils.getColor(context, color), mode.getPorterDuffMode())
    }
}

fun Drawable?.setColorFilterByAttr(
    context: Context,
    @AttrRes attrId: Int,
    mode: ColorFilterMode = ColorFilterMode.SRC_IN
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this?.colorFilter = BlendModeColorFilter(ColorUtils.getColorAttr(context, attrId), mode.getBlendMode())
    } else {
        this?.setColorFilter(ColorUtils.getColorAttr(context, attrId), mode.getPorterDuffMode())
    }
}

fun ImageView.setColorFilter(color: Int, mode: ColorFilterMode) {
    setColorFilter(color, mode.getPorterDuffMode())
}

fun ImageView.setColorFilterByRes(@ColorRes color: Int, mode: ColorFilterMode = ColorFilterMode.SRC_IN) {
    setColorFilter(ColorUtils.getColor(context, color), mode.getPorterDuffMode())
}

fun ImageView.setColorFilterByAttr(@AttrRes attrId: Int, mode: ColorFilterMode = ColorFilterMode.SRC_IN) {
    setColorFilter(ColorUtils.getColorAttr(context, attrId), mode.getPorterDuffMode())
}