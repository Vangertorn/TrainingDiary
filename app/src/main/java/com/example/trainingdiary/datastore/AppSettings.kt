package com.example.trainingdiary.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.trainingdiary.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "${BuildConfig.APPLICATION_ID}_datastore"
)

class AppSettings(context: Context) {

    private val dataStore = context.dataStore

    fun idTrainingFlow(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(TRAINING_ID_KEY)] ?: -1
    }

    fun idExerciseFlow(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(EXERCISE_ID_KEY)] ?: -1
    }

    fun reoccurrencesFlow(): Flow<String> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(REOCCURRENCES_KEY)] ?: ""
    }

    fun weightFlow(): Flow<String> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(WEIGHT_KEY)] ?: ""
    }

    fun orderAddedFlow(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(ORDER_ADDED)] ?: true
    }

    suspend fun idTraining(): Long = idTrainingFlow().first()

    suspend fun orderAdded(): Boolean = orderAddedFlow().first()


    suspend fun setIdTraining(idTraining: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(TRAINING_ID_KEY)] = idTraining
        }
    }

    suspend fun setIdExercise(idExercise: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(EXERCISE_ID_KEY)] = idExercise
        }
    }

    suspend fun setReoccurrences(reoccurrences: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(REOCCURRENCES_KEY)] = reoccurrences
        }
    }

    suspend fun setWeight(weight: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(WEIGHT_KEY)] = weight
        }
    }
    suspend fun setOrderAdded(order:Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(ORDER_ADDED)] = order
        }
    }

    companion object {
        private const val TRAINING_ID_KEY = "TRAINING_ID_KEY"
        private const val EXERCISE_ID_KEY = "EXERCISE_ID_KEY"
        private const val REOCCURRENCES_KEY = "REOCCURRENCES_KEY"
        private const val WEIGHT_KEY = "WEIGHT_KEY"
        private const val ORDER_ADDED = "ORDER_ADDED"
    }
}