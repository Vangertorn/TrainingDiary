package com.example.trainingdiary.models

import androidx.room.Embedded
import androidx.room.Relation

class TrainingInfo(
    @Embedded
    val training: Training,
    @Relation(
        parentColumn = "id",
        entityColumn = "idTraining"
    )
    val exercises: List<Exercise>?
)