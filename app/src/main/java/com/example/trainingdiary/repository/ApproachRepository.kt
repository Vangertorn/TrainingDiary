package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.ApproachDao
import com.example.trainingdiary.models.Approach
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ApproachRepository(private val approachDao: ApproachDao) {

    val currentApproachFlow: Flow<List<Approach>> = approachDao.getApproachFlow().map {
        it ?: listOf()
    }

    suspend fun saveApproach(approach: Approach) {
        withContext(Dispatchers.IO) {
            approachDao.insertApproach(approach)
        }
    }

    suspend fun deleteApproach(approach: Approach) {
        withContext(Dispatchers.IO) {
            approachDao.deleteApproach(approach)
        }
    }
}