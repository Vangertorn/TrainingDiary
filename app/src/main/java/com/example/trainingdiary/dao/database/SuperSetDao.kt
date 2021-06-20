package com.example.trainingdiary.dao.database

import androidx.room.*
import com.example.trainingdiary.models.SuperSet


@Dao
abstract class SuperSetDao {
    @Insert
    abstract fun insertSuperSet(superSet: SuperSet): Long

    @Delete
    abstract fun deleteSuperSets(superSets: List<SuperSet>)


    @Query("SELECT * FROM table_super_set WHERE deleted ==:flags")
    abstract fun getSuperSetByFlags(
        flags: Boolean
    ): List<SuperSet>


    @Update
    abstract fun updateSuperSet(superSet: SuperSet)

    @Query("SELECT * FROM table_super_set WHERE id==:idSuperSet LIMIT 1")
    abstract fun getSuperSetById(idSuperSet: Long): SuperSet


    @Transaction
    open fun updateSuperSetById(idSuperSet: Long) {
        updateSuperSet(getSuperSetById(idSuperSet))
    }

    @Query("SELECT * FROM table_super_set WHERE visibility ==:flags ")
    abstract fun getSuperSetByVisible(flags: Boolean): List<SuperSet>?

    @Transaction
    open fun deletedSuperSetByVisible() {

        val list = getSuperSetByVisible(false)
        deleteSuperSets(list ?: listOf())
    }

}