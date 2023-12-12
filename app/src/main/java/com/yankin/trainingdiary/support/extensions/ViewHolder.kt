package com.yankin.trainingdiary.support.extensions

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

val RecyclerView.ViewHolder.context: Context get() = itemView.context

val RecyclerView.ViewHolder.resources: Resources get() = itemView.resources

fun RecyclerView.ViewHolder.getString(@StringRes id: Int) = this.context.getString(id)

fun RecyclerView.ViewHolder.getString(@StringRes id: Int, vararg format: Any) =
    this.context.getString(id, *format)

fun RecyclerView.ViewHolder.getColor(@ColorRes id: Int) = ContextCompat.getColor(this.context, id)

fun RecyclerView.ViewHolder.getInteger(@IntegerRes id: Int) = this.resources.getInteger(id)
