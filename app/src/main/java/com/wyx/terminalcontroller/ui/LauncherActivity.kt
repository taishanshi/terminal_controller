package com.wyx.terminalcontroller.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wyx.common_module.sharedPreference.CommonPreferences
import com.wyx.controler.manager.YsController
import com.wyx.terminalcontroller.databinding.ActivityLoginBinding

class LauncherActivity : AppCompatActivity() {
    private val TAG: String = LauncherActivity::class.simpleName!!
    private lateinit var loginSp: CommonPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //全屏
        YsController.fullScreen()
        Log.d(TAG,"LauncherActivity onCreate")
        loginSp = CommonPreferences(this)
    }

    override fun onResume() {
        super.onResume()
        val loginUrl = loginSp.loginUrl
        Log.d(TAG,"start launcher, loginUrl = $loginUrl")
        if(!TextUtils.isEmpty(loginUrl)) {
            val intent = Intent(this@LauncherActivity, WebActivity::class.java)
            intent.putExtra("uri", loginUrl)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            val intent = Intent(this@LauncherActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}