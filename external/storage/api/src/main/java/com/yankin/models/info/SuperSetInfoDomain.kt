package com.yankin.models.info

import com.yankin.models.ExerciseDomain
import com.yankin.models.SuperSetDomain

class SuperSetInfoDomain(
    val superSetDomain: SuperSetDomain,
    val exerciseDomain: List<ExerciseDomain>?
)
