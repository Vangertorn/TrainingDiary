package com.yankin.trainingdiary.support.extensions

import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import com.google.android.material.tabs.TabLayout
import com.yankin.trainingdiary.R

fun TabLayout.setCustomText(
    text: String,
    position: Int,
    isSelected: Boolean,
    @DimenRes textSize: Int,
    @FontRes font: Int
) {
    val vg: ViewGroup = getChildAt(0) as ViewGroup
    val vgTab: ViewGroup = vg.getChildAt(position) as ViewGroup
    vgTab.forEach {
        if (it is TextView) {
            it.isAllCaps = false
            it.text = text
            it.textSize = resources.getDimensionPixelSize(textSize).toFloat()
            it.setTextColor(ContextCompat.getColor(context, R.color.black))
            if (isSelected) it.typeface = ResourcesCompat.getFont(context, font)
            else it.typeface = ResourcesCompat.getFont(context, font)
        }
    }
}
