package com.yankin.trainingdiary

import android.annotation.SuppressLint
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.MuscleGroupRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.repository.TrainingRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val exerciseRepository: ExerciseRepository,
    private val muscleGroupRepository: MuscleGroupRepository,
    private val superSetRepository: SuperSetRepository,
    private val appSettings: AppSettings
) : CoroutineViewModel() {

    fun deletedTrainings() {
        launch {
            trainingRepository.deletedTrainingsByFlags()
        }
    }

    fun deletedExercises() {
        launch {
            exerciseRepository.deleteExercises()
        }
    }

    fun deletedSuperSets() {
        launch {
            superSetRepository.deleteInvisibleSuperSet()
        }
    }

    init {
        launch {
            muscleGroupRepository.saveDefaultValues(
            )
        }

    }

    fun setLeftDays() {
        launch {
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