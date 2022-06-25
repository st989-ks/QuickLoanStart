package com.pipe.quickloanstart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.di.NavigatorModule
import com.pipe.quickloanstart.extensions.SharedPrefs
import com.pipe.quickloanstart.extensions.setLocale
import com.pipe.quickloanstart.ui.push.PushWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorModule: NavigatorModule

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var pushWorker: PushWorker


    private lateinit var navHostFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pushWorker.getPush()
        setLocale(sharedPrefs.getLanguage())
        navHostFragment =
            requireNotNull(supportFragmentManager.findFragmentById(R.id.fragmentContainer))

        val navController = navHostFragment.findNavController()

        navigatorModule.navController = navController
    }

    override fun onBackPressed() {
        sharedPrefs.setOnBackPressed(true)
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0 && sharedPrefs.getToken().isNotEmpty()) {
            navigatorModule.navigateBack()
        } else {
            finish()
        }
    }
}