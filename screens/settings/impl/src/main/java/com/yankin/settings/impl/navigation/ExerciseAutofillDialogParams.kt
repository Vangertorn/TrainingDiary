package com.yankin.settings.impl.navigation

import android.os.Parcelable
import com.yankin.settings.impl.presentation.ExerciseName
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ExerciseAutofillDialogParams(
    val exerciseName: ExerciseName,
) : Parcelable