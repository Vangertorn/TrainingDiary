package com.yankin.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yankin.trainingdiary.preferences.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.datastore.preferences.core.doublePreferencesKey

private val Context.getPreferences: DataStore<Preferences> by preferencesDataStore(
    "${BuildConfig.preferences}_ds"
)

class AppSettings(context: Context) {

    private val dataStore: DataStore<Preferences> = context.getPreferences

    fun getRepsStream(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(REPS_KEY)] ?: 0
    }

    fun getWeightStream(): Flow<Double> = dataStore.data.map { preferences ->
        preferences[doublePreferencesKey(WEIGHT_KEY)] ?: 0.0
    }

    fun orderAddedFlow(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(ORDER_ADDED)] ?: true
    }

    suspend fun orderAdded(): Boolean = orderAddedFlow().first()

    suspend fun setIdTraining(idTraining: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(TRAINING_ID_KEY)] = idTraining
        }
    }

    suspend fun setReps(reps: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(REPS_KEY)] = reps
        }
    }

    suspend fun setWeight(weight: Double) {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(WEIGHT_KEY)] = weight
        }
    }

    suspend fun setOrderAdded(order: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(ORDER_ADDED)] = order
        }
    }

    fun getIdThemeStream(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(THEME_ID_KEY)] ?: MODE_NIGHT_UNSPECIFIED
    }

    companion object {
        private const val TRAINING_ID_KEY = "TRAINING_ID_KEY"
        private const val REPS_KEY = "REPS_KEY"
        private const val WEIGHT_KEY = "WEIGHT_KEY"
        private const val ORDER_ADDED = "ORDER_ADDED"
        private const val THEME_ID_KEY = "THEME_ID_KEY"
    }
}