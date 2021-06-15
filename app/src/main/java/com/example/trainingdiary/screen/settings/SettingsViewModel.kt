package com.example.trainingdiary.screen.settings

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(private val appSettings: AppSettings): CoroutineViewModel() {

    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()

    fun saveReoccurrences(reoccurrences: String){
        launch {
            appSettings.setReoccurrences(reoccurrences)
        }
    }
    fun saveWeight(weight: String){
        launch {
            appSettings.setWeight(weight)
        }
    }

    fun saveOrderAdded(order: Boolean){
        launch {
            appSettings.setOrderAdded(order)
        }
    }

}