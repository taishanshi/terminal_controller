package com.wyx.common_module.sharedPreference

import android.content.Context

class CommonPreferences(context: Context) {
    companion object {
        /**
         * 通过传入不同的SP文件名来存储到不同的XML中
         */
        const val TERMINAL_CONTROLLER = "terminal_controller" //文件名1
    }

    var loginUrl by SharedPreferencesDelegate(context, TERMINAL_CONTROLLER, "", "login_url")
}