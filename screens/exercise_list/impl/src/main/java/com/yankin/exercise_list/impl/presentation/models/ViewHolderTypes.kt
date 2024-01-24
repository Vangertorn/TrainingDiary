package com.yankin.exercise_list.impl.presentation.models

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
