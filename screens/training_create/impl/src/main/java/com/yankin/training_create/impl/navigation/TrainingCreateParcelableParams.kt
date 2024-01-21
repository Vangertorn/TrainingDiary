package com.yankin.training_create.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class TrainingCreateParcelableParams(
    val trainingId: Long?
) : Parcelable