package com.example.trainingdiary.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.trainingdiary.BuildConfig
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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

    suspend fun idTraining(): Long = idTrainingFlow().first()

    suspend fun setIdTraining(idTraining: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(TRAINING_ID_KEY)] = idTraining
        }
    }

    suspend fun setIdExercise(idExercise: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(EXERCISE_ID_KEY)] =idExercise
        }
    }

    companion object {
        private const val TRAINING_ID_KEY = "TRAINING_ID_KEY"
        private const val EXERCISE_ID_KEY = "EXERCISE_ID_KEY"
    }
}