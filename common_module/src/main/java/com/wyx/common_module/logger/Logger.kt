package com.wyx.common_module.logger


import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.PrettyFormatStrategy

typealias log = com.orhanobut.logger.Logger

object Logger {

    init {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(2)
            .methodOffset(5)
            .tag("wyx")
            .build()

        if (BuildConfig.DEBUG) {
            val androidLogAdapter = AndroidLogAdapter(formatStrategy)
            log.addLogAdapter(androidLogAdapter)
        }
        val diskLogAdapter = WyxDiskLogAdapter()
        log.addLogAdapter(diskLogAdapter)
    }


    fun i(tag: String, msg: String) {
        log.t(tag).i(msg, tag)
        Log.i(tag, msg)
    }

    fun v(tag: String, msg: String) {
        log.t(tag).v(msg, tag)
        Log.v(tag, msg)
    }

    fun e(tag: String, msg: String) {
        log.t(tag).e(msg, tag)
        Log.e(tag, msg)
    }

    fun w(tag: String, msg: String) {
        log.t(tag).w(msg, tag)
    }

    fun d(tag: String, msg: String) {
        log.t(tag).d(msg, tag)
        Log.d(tag, msg)
    }

    fun e(tag: String, msg: String, throwable: Throwable) {
        log.t(tag).e(throwable, msg, tag)
        Log.e(tag, msg, throwable)
    }
}