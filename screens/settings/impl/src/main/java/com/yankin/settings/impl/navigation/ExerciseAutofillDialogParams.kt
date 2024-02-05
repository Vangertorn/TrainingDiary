package com.yankin.settings.impl.navigation

import android.os.Parcelable
import com.yankin.settings.impl.presentation.exercisePattern
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ExerciseAutofillDialogParams(
    val exercisePattern: exercisePattern,
) : Parcelable