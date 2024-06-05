package com.wyx.common_module.sharedPreference

import android.content.Context

class CommonPreferences(context: Context) {
    companion object {
        /**
         * 通过传入不同的SP文件名来存储到不同的XML中
         */
        const val TERMINAL_CONTROLLER = "terminal_controller" //文件名1

        const val KEY_LOGIN_URL = "login_url"

        const val KEY_LAST_LOGIN_IP = "last_login_ip"

        const val KEY_LAST_LOGIN_PORT = "last_login_port"

        const val KEY_LAST_LOGIN_VISUALIZATION = "last_login_visualization"
    }

    var loginUrl by SharedPreferencesDelegate(context, TERMINAL_CONTROLLER, "", KEY_LOGIN_URL)
    var lastLoginIP by SharedPreferencesDelegate(context, TERMINAL_CONTROLLER, "", KEY_LAST_LOGIN_IP)
    var lastLoginPort by SharedPreferencesDelegate(context, TERMINAL_CONTROLLER, "", KEY_LAST_LOGIN_PORT)
    var lastLoginVisualization by SharedPreferencesDelegate(context, TERMINAL_CONTROLLER, "", KEY_LAST_LOGIN_VISUALIZATION)
}