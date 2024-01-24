package com.yankin.exercise_list.api.navigation

data class ExerciseListParams(
    val trainingId: Long,
    val date: String,
    val muscleGroups: String?,
    val comment: String?,
    val weight: String?,
    val position: Int,
    val deleted: Boolean,
    val selectedMuscleGroup: MutableList<Int>
)