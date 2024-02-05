package com.yankin.settings.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ExercisePatternCreateDialogParams(
    val exercisePatternId: Long?,
) : Parcelable