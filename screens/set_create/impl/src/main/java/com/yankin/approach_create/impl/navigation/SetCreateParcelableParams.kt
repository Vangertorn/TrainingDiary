package com.yankin.approach_create.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed interface SetCreateParcelableParams : Parcelable {

    @Parcelize
    data class SetCreate(val exerciseId: Long) : SetCreateParcelableParams

    @Parcelize
    data class SuperSetCreate(val superSetId: Long) : SetCreateParcelableParams
}