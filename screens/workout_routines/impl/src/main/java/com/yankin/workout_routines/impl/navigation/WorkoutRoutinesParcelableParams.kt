package com.yankin.workout_routines.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class WorkoutRoutinesParcelableParams(
    val trainingId: Long,
) : Parcelable