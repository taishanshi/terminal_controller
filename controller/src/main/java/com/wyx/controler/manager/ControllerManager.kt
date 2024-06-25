package com.wyx.controler.manager

import com.wyx.controler.manager.interfaces.ControllerInterface

object ControllerManager {
    private var phoneModule = ""
    private lateinit var controller: ControllerInterface

    fun getInstance() : ControllerInterface {

        this.phoneModule = phoneModule
        return when(phoneModule) {
            ""-> YsController()
            else -> DefaultController()
        }
    }

    fun fullScreen() {

    }
}