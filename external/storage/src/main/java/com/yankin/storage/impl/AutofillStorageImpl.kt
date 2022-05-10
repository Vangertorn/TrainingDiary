package com.yankin.storage.impl

import com.yankin.models.ExerciseAutofillDomain
import com.yankin.storage.AutofillStorage
import com.yankin.storage.room.converters.toEntity
import com.yankin.storage.room.converters.toModel
import com.yankin.storage.room.dao.ExerciseAutofillDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AutofillStorageImpl(
    private val autofillDao: ExerciseAutofillDao
) : AutofillStorage {
    override fun getExerciseAutofillStringFlow(): Flow<List<String>?> {
        return autofillDao.getExerciseAutofillStringFlow()
    }

    override fun getExerciseAutofillFlow(): Flow<List<ExerciseAutofillDomain>?> {
        return autofillDao.getExerciseAutofillFlow().map { it?.map { it.toModel() } }
    }

    override fun insertExerciseAutofill(exerciseAutofillDomainEntity: ExerciseAutofillDomain) {
        autofillDao.insertExerciseAutofill(exerciseAutofillDomainEntity.toEntity())
    }

    override fun deleteExerciseAutofill(exerciseAutofillDomainEntity: ExerciseAutofillDomain) {
        autofillDao.deleteExerciseAutofill(exerciseAutofillDomainEntity.toEntity())
    }

    override fun updateExerciseAutofill(exerciseAutofillDomainEntity: ExerciseAutofillDomain) {
        autofillDao.updateExerciseAutofill(exerciseAutofillDomainEntity.toEntity())
    }
}