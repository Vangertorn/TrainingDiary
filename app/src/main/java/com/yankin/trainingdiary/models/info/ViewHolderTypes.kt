package com.yankin.trainingdiary.models.info

import com.yankin.trainingdiary.models.Approach
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.SuperSet

sealed class ViewHolderTypes(val viewType: Int) {

    class SuperSetDate(
        val superSet: SuperSet,
        val exercise: List<ExerciseInfo>?
    ) : ViewHolderTypes(0)

    class ExerciseInfo(
        val exercise: Exercise,
        val approaches: List<Approach>?
    ) : ViewHolderTypes(1)
}
