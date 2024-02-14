package com.yankin.approach_create.impl.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id: Long = 0,
    val name: String,
    val idTraining: Long? = null,
    val position: Int = 0,
    val comment: String? = null,
    val deleted: Boolean = false,
    val idSet: Long? = null
) : Parcelable
