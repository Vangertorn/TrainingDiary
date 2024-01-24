package com.yankin.exercise_create.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SuperSetCreateParcelableParams(
    val superSetId: Long
) : Parcelable