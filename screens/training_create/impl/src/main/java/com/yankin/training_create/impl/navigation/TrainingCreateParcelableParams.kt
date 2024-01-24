package com.yankin.training_create.impl.navigation

import android.os.Parcelable
import com.yankin.training_create.impl.presentation.Training
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class TrainingCreateParcelableParams(
    val training: Training?,
) : Parcelable