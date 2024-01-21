package com.yankin.trainingdiary.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.yankin.trainingdiary.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideNavController(@ActivityContext activityContext: Context): NavController {
        val activity = activityContext as FragmentActivity
        println("TAG1 activity - $activity")
        println("TAG1 navHostFragment - ${activity.supportFragmentManager.findFragmentById(R.id.navHostFragment)}")
        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        return navHostFragment.navController
    }
}