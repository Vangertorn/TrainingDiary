package com.yankin.trainingdiary.screen.training_list

import android.annotation.SuppressLint
import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.repository.TrainingRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val appSettings: AppSettings,
) : CoroutineViewModel() {
    val trainingAscLiveData = trainingRepository.currentTrainingAscFlow.asLiveData()
    val trainingDescLiveData = trainingRepository.currentTrainingDescFlow.asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()
    val numberTrainingLiveData = appSettings.numberOfTrainingSessionsFlow().asLiveData()
    val numberLeftDaysLiveData = appSettings.leftDaysFlow().asLiveData()

    fun deletedTrainingTrue(training: Training) {

        launch {
            if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                if (monthFormatter.parse(training.date)!! >= monthFormatter.parse(appSettings.getDateCreatedTicket())) {
                    appSettings.setNumberOfTrainingSessions(appSettings.getNumberOfTrainingSessions() + 1)
                }
            }

            trainingRepository.deletedTrainingTrue(training)
        }
    }

    fun getTrainingFromPosition(position: Int): Training? {
        return runBlocking {
            if (appSettings.orderAdded()) {
                return@runBlocking trainingAscLiveData.value?.get(position)
            } else {
                return@runBlocking trainingDescLiveData.value?.get(position)
            }
        }
    }

    fun numberOfTrainingSessions(): Int {
        return runBlocking {
            return@runBlocking appSettings.getNumberOfTrainingSessions()
        }
    }

    fun subscriptionEndDate(): String {
        return runBlocking {
            return@runBlocking appSettings.getSubscriptionEndDate()
        }
    }

    fun deletedTrainingFalse(training: Training) {

        launch {
            if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                if (monthFormatter.parse(training.date)!!
                    >= monthFormatter.parse(appSettings.getDateCreatedTicket())
                ) {
                    appSettings.setNumberOfTrainingSessions(appSettings.getNumberOfTrainingSessions() - 1)
                }
            }

            trainingRepository.deletedTrainingFalse(training)

        }
    }

    fun rememberIdTraining(training: Training) {
        launch {
            appSettings.setIdTraining(training.id)
        }
    }

    companion object {
        @SuppressLint("ConstantLocale")
        val monthFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    }
}