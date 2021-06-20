package com.example.trainingdiary.models.info

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.SuperSet

class SuperSetInfo(
    @Embedded
    val superSet: SuperSet,
    @Relation(
        parentColumn = "idSuperSet",
        entityColumn = "idSet"
    )
    val exercise: List<Exercise>?
)