package com.example.trainingdiary.models.info

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.Training

class TrainingInfo(
    @Embedded
    val training: Training,
    @Relation(
        parentColumn = "id",
        entityColumn = "idTraining"
    )
    val exercises: List<Exercise>?
)