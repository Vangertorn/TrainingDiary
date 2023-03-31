package com.yankin.trainingdiary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.yankin.trainingdiary.databinding.ActivityMainBinding
import com.yankin.trainingdiary.support.SupportActivityInset
import com.yankin.trainingdiary.support.setWindowTransparency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SupportActivityInset<ActivityMainBinding>() {

    override lateinit var viewBinding: ActivityMainBinding
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment }
    private val navController: NavController by lazy { navHostFragment.navController }
    private val handler = Handler(Looper.getMainLooper())
    private var doubleBackToExitPressedOnce = false
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setWindowTransparency(this)
        viewModel.deletedTrainings()
        viewModel.deletedExercises()
        viewModel.deletedSuperSets()
        viewModel.setLeftDays()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                finish()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(R.string.press_back), Toast.LENGTH_SHORT).show()
            handler.postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else navController.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun getActiveFragment(): Fragment? {
        return navHostFragment.childFragmentManager.fragments[0]
    }
}
