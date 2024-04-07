package com.yankin.trainingdiary

import android.app.Application
import com.yankin.api.AppThemeOwner
import com.yankin.api.models.ThemeModel
import com.yankin.trainingdiary.di.ThemeFactory
import com.yankin.trainingdiary.di.WorkersFactory
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiaryApp : Application(), AppThemeOwner {

    override lateinit var theme: ThemeModel

    override fun onCreate() {
        val themeFactory = EntryPointAccessors.fromApplication(this, ThemeFactory::class.java)
        val getThemeModelUseCase = themeFactory.getThemeModelUseCase()
        theme = getThemeModelUseCase.invoke()

        val workersFactory = EntryPointAccessors.fromApplication(this, WorkersFactory::class.java)
        workersFactory.getClearDeleteQueueLauncher().launch()

        super.onCreate()
    }
}
