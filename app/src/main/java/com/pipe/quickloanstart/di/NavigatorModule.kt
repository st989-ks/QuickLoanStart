package com.pipe.quickloanstart.di

import androidx.annotation.IdRes
import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorModule @Inject constructor() {

    lateinit var navController: NavController

    fun navigate(@IdRes navigationId: Int) {
        navController.navigate(navigationId)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}