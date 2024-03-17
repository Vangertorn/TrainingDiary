package com.yankin.approach_create.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class SetCreateParcelableParams(
    val trainingBlockId: Long,
) : Parcelable