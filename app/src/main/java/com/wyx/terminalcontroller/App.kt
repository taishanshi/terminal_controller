package com.wyx.terminalcontroller

import android.app.Application
import com.wyx.controler.manager.YsController
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    private val TAG: String = "App"
    override fun onCreate() {
        super.onCreate()
        YsController.init(this)
        YsController.fullScreen()
    }
}