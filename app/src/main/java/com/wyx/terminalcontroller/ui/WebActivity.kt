package com.wyx.terminalcontroller.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wyx.common_module.extensions.log
import com.wyx.common_module.sharedPreference.CommonPreferences
import com.wyx.terminalcontroller.databinding.ActivityWebBinding


class WebActivity : AppCompatActivity() {
    private val TAG: String = WebActivity::class.simpleName!!
    private lateinit var binding: ActivityWebBinding
    private lateinit var loginSp: CommonPreferences

    var retryTimes = 0

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginSp = CommonPreferences(this)

        if(intent != null) {
            val uriString:String = intent.getStringExtra("uri")!!
            TAG.log("uriString : $uriString")


            val webSettings = binding.myWebView.settings
            webSettings?.javaScriptEnabled = true

            binding.myWebView.webViewClient = object: WebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    error?.run {
                        retry(request?.url.toString(), description.toString())
                    }
                }


                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)

                    errorResponse?.run {
                        binding.loading.visibility = View.GONE
                        TAG.log("onReceivedHttpError : ${this.data}")
                    }

                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    // Page loaded successfully, cancel timeout
                    TAG.log("onPageFinished. isFinishing = $isFinishing")
                    if(isFinishing) {
                        binding.loading.visibility = View.GONE
                    }
                }
            }

            binding.loading.visibility = View.VISIBLE
            retryTimes = 0
            binding.myWebView.loadUrl(uriString)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun retry(uriString : String, error : String) {
        TAG.log("网页错误:${error}, 开始重试： ${retryTimes}")


//        Thread.sleep(3000)
        if(retryTimes >= 3) {
            loginSp.loginUrl = ""
            finish()
        } else {
            Toast.makeText(this@WebActivity, "网页错误：${error}, 开始重试： ${retryTimes + 1}", Toast.LENGTH_SHORT).show()
            Thread.sleep(2000)

            retryTimes++
            binding.loading.visibility = View.VISIBLE
            binding.myWebView.loadUrl(uriString)
        }
    }
}