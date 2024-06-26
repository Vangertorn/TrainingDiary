package com.yankin.trainingdiary.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.exercise.api.usecases.DeleteExercisesUseCase
import com.yankin.super_set.api.usecases.DeleteSuperSetsUseCase
import com.yankin.muscle_groups.api.usecases.SaveDefaultMuscleGroupListUseCase
import com.yankin.training.api.usecases.DeleteTrainingByFlagsUseCase
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.presentation.models.MuscleGroup
import com.yankin.trainingdiary.presentation.models.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val deleteSuperSetsUseCase: DeleteSuperSetsUseCase,
    private val appSettings: AppSettings,
    private val saveDefaultMuscleGroupListUseCase: SaveDefaultMuscleGroupListUseCase,
    @ApplicationContext private val context: Context,
    private val deleteTrainingByFlagsUseCase: DeleteTrainingByFlagsUseCase,
    private val deleteExercisesUseCase: DeleteExercisesUseCase,
) : ViewModel() {

    fun deletedTrainings() {
        viewModelScope.launch {
            deleteTrainingByFlagsUseCase.invoke()
        }
    }

    fun deletedExercises() {
        viewModelScope.launch {
            deleteExercisesUseCase.invoke()
        }
    }

    fun deletedSuperSets() {
        viewModelScope.launch {
            deleteSuperSetsUseCase.invoke()
        }
    }

    init {
        viewModelScope.launch {
            saveDefaultMuscleGroupListUseCase.invoke(
                listOf(
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.legs), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(R.string.all_muscle_groups),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.breast), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.biceps), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(R.string.shoulders),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.back), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.triceps), factorySettings = true)
                ).map { it.toDomain() }
            )
        }
    }

    fun setLeftDays() {
        viewModelScope.launch {
            val leftDays = daysAmount()
            if (leftDays.toInt() < 365) {
                if (leftDays.toInt() < 0) {
                    appSettings.setNumberOfTrainingSessions(-1)
                    appSettings.setSubscriptionEndDate("")
                    appSettings.setDayLeft(-1)
                    appSettings.setDateCreatedTicket("")
                } else {
                    appSettings.setDayLeft(leftDays.toInt())
                }
            }
        }
    }

    private fun daysAmount(): String {
        return runBlocking {
            if (appSettings.getSubscriptionEndDate().isEmpty()) {
                return@runBlocking "-1"
            } else {

                val dateEnd = dateFormatter.parse(appSettings.getSubscriptionEndDate())!!.time
                if (dateEnd == 0L) {
                    return@runBlocking "365"
                } else {
                    val currentDate = Date().time
                    val result = dateEnd - currentDate
                    return@runBlocking dayFormatter.format(result)
                }
            }
        }
    }

    companion object {
        @SuppressLint("ConstantLocale")
        private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

        @SuppressLint("ConstantLocale")
        private val dayFormatter = SimpleDateFormat("d", Locale.getDefault())
    }
}
