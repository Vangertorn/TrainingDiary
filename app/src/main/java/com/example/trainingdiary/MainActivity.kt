package com.example.trainingdiary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.trainingdiary.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import com.example.myapplication.support.SupportActivityInset
import com.example.myapplication.support.setWindowTransparency
import com.example.trainingdiary.screen.training_list.TrainingListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : SupportActivityInset<ActivityMainBinding>() {

    override lateinit var viewBinding: ActivityMainBinding
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment }
    private val navController: NavController by lazy { navHostFragment.navController }
    private val handler = Handler(Looper.getMainLooper())
    private var doubleBackToExitPressedOnce = false
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setWindowTransparency(this)
        viewModel.deletedTrainings()
        viewModel.deletedExercises()
    }



    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                finish()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press \"back\" again to exit", Toast.LENGTH_SHORT).show()
            handler.postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
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


