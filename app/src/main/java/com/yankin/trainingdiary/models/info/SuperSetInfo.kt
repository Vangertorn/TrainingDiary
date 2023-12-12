package com.yankin.trainingdiary.models.info

import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.SuperSet

class SuperSetInfo(
    val superSet: SuperSet,
    val exercise: List<Exercise>?
)
