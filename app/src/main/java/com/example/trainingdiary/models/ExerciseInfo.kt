package com.example.trainingdiary.models

import androidx.room.Embedded
import androidx.room.Relation

class ExerciseInfo(
    @Embedded
    val exercise: Exercise,
    @Relation(parentColumn = "id", entityColumn = "idExercise")
    val approaches: List<Approach>?
)