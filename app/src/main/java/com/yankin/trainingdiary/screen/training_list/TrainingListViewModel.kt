package com.yankin.trainingdiary.screen.training_list

import android.annotation.SuppressLint
import androidx.lifecycle.asLiveData
import com.yankin.preferences.AppSettings
import com.yankin.training.api.usecases.DeleteTrainingFalseUseCase
import com.yankin.training.api.usecases.DeleteTrainingTrueUseCase
import com.yankin.training.api.usecases.GetCurrentTrainingAscStreamUseCase
import com.yankin.training.api.usecases.GetCurrentTrainingDescStreamUseCase
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val deleteTrainingTrueUseCase: DeleteTrainingTrueUseCase,
    private val deleteTrainingFalseUseCase: DeleteTrainingFalseUseCase,
    getCurrentTrainingAscStreamUseCase: GetCurrentTrainingAscStreamUseCase,
    getCurrentTrainingDescStreamUseCase: GetCurrentTrainingDescStreamUseCase,
) : CoroutineViewModel() {
    val trainingAscLiveData = getCurrentTrainingAscStreamUseCase.invoke().map { it.map { it.toModel() } }.asLiveData()
    val trainingDescLiveData = getCurrentTrainingDescStreamUseCase.invoke().map { it.map { it.toModel() } }.asLiveData()
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

            deleteTrainingTrueUseCase.invoke(training.toDomain())
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

            deleteTrainingFalseUseCase.invoke(training.toDomain())
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
