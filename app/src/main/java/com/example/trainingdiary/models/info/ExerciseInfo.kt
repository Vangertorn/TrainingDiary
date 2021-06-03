package com.example.trainingdiary.models.info

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise

class ExerciseInfo(
    @Embedded
    val exercise: Exercise,
    @Relation(parentColumn = "id", entityColumn = "idExercise")
    val approaches: List<Approach>?
)