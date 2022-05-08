package com.yankin.trainingdiary.models.info

import androidx.room.Embedded
import androidx.room.Relation
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.SuperSet

class SuperSetInfo(
    @Embedded
    val superSet: SuperSet,
    @Relation(
        parentColumn = "id",
        entityColumn = "idSet"
    )
    val exercise: List<Exercise>?
)
