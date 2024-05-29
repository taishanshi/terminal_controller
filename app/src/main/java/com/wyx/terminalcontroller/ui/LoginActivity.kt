package com.wyx.terminalcontroller.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wyx.common_module.extensions.log
import com.wyx.common_module.sharedPreference.CommonPreferences
import com.wyx.controler.manager.YsController
import com.wyx.terminalcontroller.R
import com.wyx.terminalcontroller.databinding.ActivityLoginBinding


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginActivity : AppCompatActivity() {
    private val TAG: String = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginSp: CommonPreferences
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        YsController.fullScreen()

        loginSp = CommonPreferences(this)
    }

    private fun initView() {
        binding.appVersion.text = resources.getString(R.string.login_version, getAppVersion(this@LoginActivity))
        binding.loginBtn.setOnClickListener {
            var visualization = binding.loginVisualizationEdittext.text
            var host = binding.loginIpEdittext.text
            var port = binding.loginPortEdittext.text

            if(TextUtils.isEmpty(host) || TextUtils.isEmpty(port) || TextUtils.isEmpty(visualization) ) {
                Toast.makeText(this@LoginActivity, "不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uriSb = StringBuilder()
            uriSb.append("http://")
            uriSb.append(host)
            uriSb.append(":")
            uriSb.append(port)
            uriSb.append("/")
            uriSb.append(visualization)
            uriSb.append(".htm")

            val uri = Uri.parse(uriSb.toString())

            if(!uri.isAbsolute) {
                Toast.makeText(this@LoginActivity, "地址不正确，请重新输入！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val uriString = uriSb.toString()
            loginSp.loginUrl = uriString
            val intent = Intent(this@LoginActivity, WebActivity::class.java)
            intent.putExtra("uri", uriString)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        var clickTimes = 0
        var lastClickTime = 0L
        binding.logo.setOnClickListener {
            TAG.log("1111-时间范围内: ${System.currentTimeMillis() - lastClickTime}.")
            if(System.currentTimeMillis() - lastClickTime < 2000) {
                clickTimes += 1
            } else {
                clickTimes = 1
            }
            lastClickTime = System.currentTimeMillis()

            TAG.log("点击次数: $clickTimes.")
            if(clickTimes >= 7) {
                TAG.log("连续点击七次，退出全屏.")
                Toast.makeText(this@LoginActivity, "已退出全屏。", Toast.LENGTH_LONG).show()
                YsController.unFullScreen()
                clickTimes = 0
                //finish()
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    private fun getAppVersion(context: Context): String {
        var versionName = ""
        try {
            // 获取PackageManager实例
            val packageManager = context.packageManager
            // 获取包信息，参数1为应用包名，参数2传0表示获取版本信息
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            // 获取版本号
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }

}