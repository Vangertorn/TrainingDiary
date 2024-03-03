package com.yankin.training_exercises.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class TrainingExercisesParcelableParams(
    val trainingId: Long,
) : Parcelable