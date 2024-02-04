package com.yankin.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yankin.trainingdiary.preferences.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED

private val Context.getPreferences: DataStore<Preferences> by preferencesDataStore(
    "${BuildConfig.preferences}_ds"
)

class AppSettings(context: Context) {

    private val dataStore: DataStore<Preferences> = context.getPreferences

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

    fun idSuperSetFlow(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(SUPERSET_ID_KEY)] ?: -1
    }

    suspend fun getIdTraining(): Long = idTrainingFlow().first()

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

    suspend fun setOrderAdded(order: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(ORDER_ADDED)] = order
        }
    }

    suspend fun setSuperSetId(superSetId: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(SUPERSET_ID_KEY)] = superSetId
        }
    }

    fun getIdThemeStream(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(THEME_ID_KEY)] ?: MODE_NIGHT_UNSPECIFIED
    }

    companion object {
        private const val TRAINING_ID_KEY = "TRAINING_ID_KEY"
        private const val EXERCISE_ID_KEY = "EXERCISE_ID_KEY"
        private const val SUPERSET_ID_KEY = "SUPERSET_ID_KEY"
        private const val REOCCURRENCES_KEY = "REOCCURRENCES_KEY"
        private const val WEIGHT_KEY = "WEIGHT_KEY"
        private const val ORDER_ADDED = "ORDER_ADDED"
        private const val THEME_ID_KEY = "THEME_ID_KEY"
    }
}