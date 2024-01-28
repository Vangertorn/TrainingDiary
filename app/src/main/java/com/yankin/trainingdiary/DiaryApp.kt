package com.yankin.trainingdiary

import android.app.Application
import com.yankin.api.AppThemeOwner
import com.yankin.api.models.ThemeModel
import com.yankin.trainingdiary.di.ThemeFactory
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiaryApp : Application(), AppThemeOwner {

    override lateinit var theme: ThemeModel

    override fun onCreate() {
        val factory = EntryPointAccessors.fromApplication(this, ThemeFactory::class.java)
        val getThemeModelUseCase = factory.getThemeModelUseCase()
        theme = getThemeModelUseCase.invoke()
        super.onCreate()
    }
}
