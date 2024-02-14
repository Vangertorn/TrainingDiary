package com.yankin.approach_create.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ApproachCreateParcelableParams(
    val exerciseId: Long,
    val name: String,
    val trainingId: Long,
    val position: Int,
    val comment: String?,
    val deleted: Boolean,
    val idSet: Long?,
) : Parcelable